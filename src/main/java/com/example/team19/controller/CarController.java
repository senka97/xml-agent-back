package com.example.team19.controller;

import com.example.team19.dto.CarDTO;
import com.example.team19.dto.CarStatisticDTO;
import com.example.team19.dto.PriceListDTO;
import com.example.team19.service.impl.CarServiceImpl;
import com.example.team19.service.impl.PriceListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(value="/car/mostKilometers", produces = "application/json")
    public CarStatisticDTO getCarWithMostKilometers()  {
        return carService.getCarWithMostKilometers();
    }

    @GetMapping(value="/car/bestScore", produces = "application/json")
    public CarStatisticDTO getCarWithBestScore()  {
        return carService.getCarWithBestScore();
    }

    @GetMapping(value="/car/mostComments", produces = "application/json")
    public CarStatisticDTO getCarWithMostComments()  {
        return carService.getCarWithMostComments();
    }

    @GetMapping(value="/car/{id}/photo", produces = "application/json")
    public ArrayList<String> getCarPhotos(@PathVariable("id") Long id)  {
        return carService.getCarPhotos(id);
    }
}
