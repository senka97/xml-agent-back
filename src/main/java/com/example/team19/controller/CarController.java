package com.example.team19.controller;

import com.example.team19.dto.CarDTO;
import com.example.team19.dto.PriceListDTO;
import com.example.team19.service.impl.CarServiceImpl;
import com.example.team19.service.impl.PriceListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class CarController {

    @Autowired
    private CarServiceImpl carService;

    @GetMapping(value="/car", produces = "application/json")
    public ArrayList<CarDTO> getOneCar()  {

        return carService.getAllAvailableCars();
    }
}
