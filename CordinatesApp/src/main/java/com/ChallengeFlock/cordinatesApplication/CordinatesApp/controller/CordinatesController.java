package com.ChallengeFlock.cordinatesApplication.CordinatesApp.controller;

import com.ChallengeFlock.cordinatesApplication.CordinatesApp.model.ProvinceCordinates;
import com.ChallengeFlock.cordinatesApplication.CordinatesApp.service.CordinatesService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cordinates_data")
public class CordinatesController {

    private final CordinatesService cordinatesService;

    public CordinatesController(CordinatesService cordinatesService) {
        this.cordinatesService = cordinatesService;
    }

    /**
     *
     *
     * @return
     */
    @GetMapping(value = "/obtain_geo_cords")
    public ResponseEntity<String> obtainGeographicCords(@ModelAttribute ProvinceCordinates cordinates, Model model) {
        model.addAttribute("provinces", cordinates);
        String name = cordinates.getName();
        return cordinatesService.obtainProvinceCordsWithName(name);
    }

    @GetMapping(value = "/obtain_geo_cords/{name}")
    public ResponseEntity<String> obtainGeographicCords(@PathVariable String name) {
        return cordinatesService.obtainProvinceCordsWithName(name);
    }


}
