package com.ChallengeFlock.cordinatesApplication.CordinatesApp.service;

import com.ChallengeFlock.cordinatesApplication.CordinatesApp.model.ProvinceCordinates;
import com.ChallengeFlock.cordinatesApplication.CordinatesApp.model.ProvinceObject;
import com.ChallengeFlock.cordinatesApplication.CordinatesApp.repository.CordinatesRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class CordinatesService {

    private static final Logger logger = LoggerFactory.getLogger(CordinatesService.class);

    private final CordinatesRepository cordinatesRepository;

    public CordinatesService(CordinatesRepository cordinatesRepository) {
        this.cordinatesRepository = cordinatesRepository;
    }

    //returnProvinceData
    public ResponseEntity<String> obtainProvinceCordinates(String name) {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = "https://apis.datos.gob.ar/georef/api/provincias?nombre=";
        ResponseEntity<String> response =
                restTemplate.getForEntity(fooResourceUrl + name, String.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode jsonName = root.path("lat");
            Assertions.assertNotNull(jsonName.asText());
        } catch (JsonMappingException e) {
            System.out.println("A JsonMappingException has occurred");
            logger.error("JsonMappingException was trowed, when tryng to obtain the data mapping from the province");
            e.printStackTrace();
            return new ResponseEntity<>("An error has occurred mapping the data API", HttpStatus.CONFLICT);

        } catch (JsonProcessingException e) {
            System.out.println("A JsonProcessingException has occurred");
            logger.error("JsonProcessingException was trowed, when tryng to process the json data of the province");
            e.printStackTrace();
            return new ResponseEntity<>("An error has occurred processing the data API", HttpStatus.CONFLICT);
        }
        //return new ResponseEntity<String("Successfully province data obtained",HttpStatus.OK);
        return response;
    }

    public ResponseEntity<String> obtainProvinceCordsWithName(String name) {
        ProvinceCordinates province = new ProvinceCordinates();
        RestTemplate restTemplate = new RestTemplate();

        String fooResourceUrl = "https://apis.datos.gob.ar/georef/api/provincias?nombre=";
        String response = restTemplate.getForObject(fooResourceUrl + name, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode rootNode = objectMapper.readTree(response);
            JsonNode latNode = rootNode.findPath("lat");
            JsonNode longNode = rootNode.findPath("lon");

            province.setLatitude(latNode.asDouble());
            province.setLongitude(longNode.asDouble());

//            if (name.equals(" ")) {
//                logger.warn("The name of the province does not exist");
//                return new ResponseEntity<>("The name of the province does not exist", HttpStatus.NOT_ACCEPTABLE);
//            }
            province.setName(name);
            saveHistoryRecords(province);
            logger.info("Province Cordinates Name obtained: " + province.getName());

        } catch (JsonProcessingException e) {
            System.out.println("A JsonProcessingException has occurred");
            logger.error("JsonMappingException was trowed, when tryng to obtain the data mapping from the province: " + province.getName());
            e.printStackTrace();
            return new ResponseEntity<>("An error has occurred processing the data API", HttpStatus.CONFLICT);
        } catch (NullPointerException e) {
            System.out.println("A NullPointerException has occurred");
            logger.error("NullPointerException was trowed, when tryng to process a null json value of the province: " + province.getName());
            e.printStackTrace();
            return new ResponseEntity<>("A null json field was intended to process by the API data", HttpStatus.CONFLICT);
        }
        logger.info("Province Latitude: " + province.getLatitude());
        logger.info("Province Longitude: " + province.getLongitude());

        return new ResponseEntity<>(province.returnLatAndLong(province.getLatitude(), province.getLongitude()), HttpStatus.OK);
    }

    //guarda la data de la provincia importante en una base como seguimiento de un historial. List creo que es ambiguo usar en return y en implementacion con list.add(data)
    public ResponseEntity<String> saveHistoryRecords(ProvinceCordinates provinceData) {
        ProvinceObject prov = new ProvinceObject(provinceData);
        ProvinceCordinates dataProvince = new ProvinceCordinates();

        Optional<ProvinceCordinates> checkProvince = Optional.ofNullable(prov.getProvinceCordinates());
        List<ProvinceCordinates> dataList = new ArrayList<>();
        dataList = cordinatesRepository.findAll();

        Iterator<ProvinceCordinates> dataListIt = dataList.iterator();

        if (checkProvince.isPresent()) {
            int indexProvince = 0;
            String keyExistingDb;
            String keyNew;
            boolean flag = true;

            while (dataListIt.hasNext()) {
                dataListIt.next();
                keyExistingDb = dataList.get(indexProvince).getName();
                keyNew = provinceData.getName();
                if (keyNew.equals(keyExistingDb)) {
                    logger.info("Province already exist in db history");
                    flag = false;
                    break;
                } else {
                    //dataProvince = cordinatesRepository.save(provinceData);
                    //logger.info("Province data saved in db successfully");
                    logger.info("Checking province if already exist...");
                    //flag = true;
                }
                indexProvince++;
            }
            if (flag) {
                dataProvince = cordinatesRepository.save(provinceData);
                logger.info("Province data saved in db successfully");
            }

        } else {
            logger.warn("Province data was null or already exist in db");
        }
        return ResponseEntity.ok("Province data saved in db successfully");
    }

    public List<ProvinceCordinates> findAllProvinces() {
        return cordinatesRepository.findAll();
    }
}
