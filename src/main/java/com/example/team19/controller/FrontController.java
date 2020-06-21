package com.example.team19.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontController {

    @RequestMapping({ "", "/login", "/registration" ,"/newAd", "/reserveCar", "/statistic", "/priceList", "/requests/**", "/newRequests"})
    public String gui() {
        return "forward:/index.html";
    }
}
