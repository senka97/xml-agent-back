package com.example.team19.controller;


import com.example.team19.dto.PriceListDTO;
import com.example.team19.service.impl.PriceListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class PriceListController {

    @Autowired
    private PriceListServiceImpl priceListService;

    @GetMapping(value="/priceList", produces = "application/json")
    public ArrayList<PriceListDTO> search()  {
        return priceListService.getAll();
    }
}
