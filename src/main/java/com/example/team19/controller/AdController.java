package com.example.team19.controller;

import com.example.team19.dto.AdDTO;
import com.example.team19.dto.AdDTO2;
import com.example.team19.model.Advertisement;
import com.example.team19.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
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


    @PreAuthorize("hasRole('ROLE_AGENT')")
    @GetMapping(value="/ads", produces = "application/json")
    public ResponseEntity<?> search()  {
        // sada vraca sve oglase, nije implementirana pretraga
        return new ResponseEntity<>(adService.searchAds(),HttpStatus.OK);
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

    @GetMapping(value="/ads/{id}", produces = "application/json")
    public ResponseEntity<?> getAd(@PathVariable("id") Long id)
    {
        AdDTO2 ad=adService.getAd(id);
        if(ad != null)
        {
            return new ResponseEntity<>(ad,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
