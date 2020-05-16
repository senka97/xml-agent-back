package com.example.team19.controller;

import com.example.team19.dto.AdDTO;
import com.example.team19.model.Advertisement;
import com.example.team19.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class AdController {

    @Autowired
    private AdServiceImpl adService;

    @Autowired
    private CarBrandServiceImpl carBrandService;

    @Autowired
    private CarModelServiceImpl carModelService;

    @Autowired
    private CarServiceImpl carService;

    @Autowired
    private PriceListServiceImpl priceListService;

    @GetMapping(value="/ads", produces = "application/json")
    public ArrayList<AdDTO> search()  {
        // sada vraca sve oglase, nije implementirana pretraga
        return adService.searchAds();
    }

    @PostMapping(value="/ad",consumes="application/json")
    public ResponseEntity<?> createNewAd(@RequestBody AdDTO newAdDTO)  {

        if(newAdDTO.getCar().getCarModel() == null || newAdDTO.getCar().getCarClass() == null || newAdDTO.getCar().getFuelType() == null || newAdDTO.getCar().getTransType() == null){
            return new ResponseEntity<>("All information about car must be entered!",HttpStatus.BAD_REQUEST);
        }

        if(newAdDTO.getStartDate() == null || newAdDTO.getEndDate() == null){
            return new ResponseEntity<>("Both start and end date must be selected",HttpStatus.BAD_REQUEST);
        }

        if(newAdDTO.getEndDate().isBefore(newAdDTO.getStartDate())){
            return new ResponseEntity<>("Start date must be before end date!",HttpStatus.BAD_REQUEST);
        }

        if(newAdDTO.getPriceList().getId() == null){
            return new ResponseEntity<>("Price list must be selected!",HttpStatus.BAD_REQUEST);
        }

        if(newAdDTO.getCar().getChildrenSeats() < 0){
            return new ResponseEntity<>("Number of seats must be greater or equal to 0",HttpStatus.BAD_REQUEST);
        }

        if(newAdDTO.getCar().getChildrenSeats() > 4){
            return new ResponseEntity<>("Number of seats must be lower than 5",HttpStatus.BAD_REQUEST);
        }

        Advertisement ad = adService.createNewAd(newAdDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
