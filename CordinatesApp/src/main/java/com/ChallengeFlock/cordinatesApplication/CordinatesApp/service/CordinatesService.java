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

    /**
     *obtainProvinceCordsWithName(name)
     * obtiene por parametro el string del nombre de la provincia, devuelve un responseEntity con la latitud y longitud de la provincia.
     * Utilizando ObjectMapper para mapear la respuesta de la Api Publica y JsonNode para pasarle los Json
     */
    public ResponseEntity<String> obtainProvinceCordsWithName(String name) {
        ProvinceCordinates province = new ProvinceCordinates();
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://apis.datos.gob.ar/georef/api/provincias?nombre="; //url de la api
        String response = restTemplate.getForObject(url + name, String.class);// construye la request con string de parametro name

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            JsonNode rootNode = objectMapper.readTree(response); //obtiene la respuesta json de la api
            JsonNode latNode = rootNode.findPath("lat");// obtiene el parametro clave lat de latitud
            JsonNode longNode = rootNode.findPath("lon");// obtiene el parametro clave lon de longitud

            province.setLatitude(latNode.asDouble());
            province.setLongitude(longNode.asDouble());
            province.setName(name);

            saveHistoryRecords(province);// guarda el objecto province con sus atributos lat y long obtenidos

            logger.info("Province Cordinates Name obtained: " + province.getName());

//            var isReal = province.isFound(); // flag para chequear si la provincia existe

//            if (!isReal) {
//                return new ResponseEntity<>("Province does not exist!",HttpStatus.NOT_FOUND); // devuelve una respuesta, que no encontro nada
//            }

        } catch (JsonProcessingException e) {
            System.out.println("A JsonProcessingException has occurred");
            logger.error("A JsonMappingException was thrown, when tried to obtain the data mapping from the province: " + province.getName());
            e.printStackTrace();
            return new ResponseEntity<>("An error has occurred processing the data API", HttpStatus.CONFLICT);
        } catch (NullPointerException e) {
            System.out.println("A NullPointerException has occurred");
            logger.error("NullPointerException was thrown, when tried to process a null json value of the province: " + province.getName());
            e.printStackTrace();
            return new ResponseEntity<>("A null json field was intended to process by the API data", HttpStatus.CONFLICT);
        }
        logger.info("Province Latitude: " + province.getLatitude());
        logger.info("Province Longitude: " + province.getLongitude());

        return new ResponseEntity<>(province.returnLatAndLong(province.getLatitude(), province.getLongitude()), HttpStatus.OK);
    }

    /**
     * saveHistoryRecords(provinceData)
     * Guarda la data de la provincia importante en una base como seguimiento de un historial.Compara con los registros de la tabla
     * Comparando los datos de la provincia,si no existe la provincia en el historial la agrega  y si no es el caso, no.
     */
    public ResponseEntity<String> saveHistoryRecords(ProvinceCordinates provinceData) {
        ProvinceObject prov = new ProvinceObject(provinceData);
        ProvinceCordinates dataProvince = new ProvinceCordinates();

        Optional<ProvinceCordinates> checkProvince = Optional.ofNullable(prov.getProvinceCordinates()); //chequea si el objeto es nulo
        List<ProvinceCordinates> dataList = new ArrayList<>();
        dataList = cordinatesRepository.findAll();//Obtiene la informacion de la base existente para analizar mas adelante

        Iterator<ProvinceCordinates> dataListIt = dataList.iterator();// Itera la lista

        if (checkProvince.isPresent()) { //vuelve a chequear si es null
            int indexProvince = 0;
            String keyExistingDb;
            double lat = provinceData.getLatitude();
            double lon = provinceData.getLongitude();
            double latDb;
            double lonDb;


            String keyNew;
            boolean flag = true;

            //Itera cada elemento de la lista obtenida por la tabla, comprobando que la provincia buscada exista en la base
            while (dataListIt.hasNext()) {
                dataListIt.next();
                keyExistingDb = dataList.get(indexProvince).getName();
                keyNew = provinceData.getName();

                latDb = dataList.get(indexProvince).getLatitude();
                lonDb = dataList.get(indexProvince).getLongitude();

                var latConversion = String.valueOf(lat);
                var lonConversion = String.valueOf(lon);

                var keyLatDb = String.valueOf(latDb);
                var keyLonDb = String.valueOf(lonDb);


                if (keyNew.equals(keyExistingDb) || latConversion.equals(keyLatDb) && lonConversion.equals(keyLonDb)) {
                    logger.info("Province already exist in db history");
                    flag = false;
                    provinceData.setFound(true);
                    break;
                } else if(latConversion.equals("0.0") && lonConversion.equals("0.0")) {
                    logger.info("Province doesn't exist");
                    flag = false;
                    provinceData.setFound(false);
                } else {
                    logger.info("Checking province if already exist...");
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
