package com.ChallengeFlock.cordinatesApplication.CordinatesApp.controller;

import com.ChallengeFlock.cordinatesApplication.CordinatesApp.service.CordinatesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private CordinatesService cordinatesService;

    /**
     * Redirige al home, es accesible sin logearse  previamente.
     */
    @GetMapping("/")
    public String showHome() {
        return "home";
    }

    /**
     * Devuelve el menu principal de la api.
     */
    @RequestMapping("/index_cordinates")
    public String showAppView(@ModelAttribute String name) {
        return "indexCordinates";
    }

    /**
     * Devuelve el historial de cordenadas de la api.
     */
    @RequestMapping("/index_history")
    public String showHistoryPage(Model model) {
        model.addAttribute("history", cordinatesService.findAllProvinces());
        return "historyPage";
    }
}
