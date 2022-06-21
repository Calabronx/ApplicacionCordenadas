package com.ChallengeFlock.cordinatesApplication.CordinatesApp.controller;

import com.ChallengeFlock.cordinatesApplication.CordinatesApp.service.CordinatesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cordinates_data")
public class CordinatesController {

    private final CordinatesService cordinatesService;

    public CordinatesController(CordinatesService cordinatesService) {
        this.cordinatesService = cordinatesService;
    }

    @GetMapping(value = "/obtain_geo_cords/{name}")
    public ResponseEntity<String> obtainGeographicCords(@PathVariable String name) {
        return cordinatesService.obtainProvinceCordsWithName(name);
    }

}
