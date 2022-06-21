package com.ChallengeFlock.cordinatesApplication.CordinatesApp.controller;

import com.ChallengeFlock.cordinatesApplication.CordinatesApp.service.CordinatesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

// /api/v1/cordinates_data
@RestController
@RequestMapping("/api/v1/cordinates_data")
public class CordinatesController {

    private static final Logger logger = LoggerFactory.getLogger(CordinatesService.class);

    private final CordinatesService cordinatesService;

    public CordinatesController(CordinatesService cordinatesService) {
        this.cordinatesService = cordinatesService;
    }

    ///cordinates_data/province_info/Sgo. del Estero
    @GetMapping(value = "/province_info/{name}")  // just for samples
    public String obtainApiCordinatesData(@PathVariable String name) throws Exception {
        String provinceName = name;
        String body =
                "<HTML><body> <a href=\"https://apis.datos.gob.ar/georef/api/provincias?nombre=" + provinceName + "\">Link click to go</a></body></HTML>";
        return (body);
    }

    @GetMapping(value = "/country_cords/{name}")
    public List<Object> getCords(@PathVariable String name) {
        String url = "https://apis.datos.gob.ar/georef/api/provincias?nombre=" + name;
        RestTemplate restTemplate = new RestTemplate();

        Object[] cordenates = restTemplate.getForObject(url, Object[].class);

        assert cordenates != null;
        return Arrays.asList(cordenates);
    }

    @GetMapping(value = "/testProvince/{name}")
    public ResponseEntity<String> getJsons(@PathVariable String name) {
        return cordinatesService.obtainProvinceCordinates(name);
    }
    //localhost:8080/api/v1/cordinates_data/obtain_geo_cords/Sgo. del Estero

    @GetMapping(value = "/obtain_geo_cords/{name}")
    public ResponseEntity<String> obtainGeographicCords(@PathVariable String name) {
        logger.debug("NAME PROVINCE DEBUG: " + name);
        return cordinatesService.obtainProvinceCordsWithName(name);
    }

}
