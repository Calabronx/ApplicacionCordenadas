package com.ChallengeFlock.cordinatesApplication.CordinatesApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public final class LoginController {

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
}
