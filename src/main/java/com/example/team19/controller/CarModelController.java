package com.example.team19.controller;

import com.example.team19.dto.AdDTO;
import com.example.team19.dto.CarModelDTO;
import com.example.team19.model.CarModel;
import com.example.team19.service.CarModelService;
import com.example.team19.service.impl.CarModelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class CarModelController {

    @Autowired
    private CarModelServiceImpl carModelService;

    @GetMapping(value="/carModel", produces = "application/json")
    public ArrayList<CarModelDTO> search()  {
        return carModelService.getAll();
    }

}
