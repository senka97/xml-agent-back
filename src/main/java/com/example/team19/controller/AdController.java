package com.example.team19.controller;

import com.example.team19.dto.AdDTO;
import com.example.team19.dto.AdDTO2;
import com.example.team19.dto.AdSearchDTO;
import com.example.team19.dto.CarDTO;
import com.example.team19.model.Advertisement;
import com.example.team19.service.impl.*;
import com.example.team19.soap.AdClient;
import com.example.team19.wsdl.PostAdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @Autowired
    private AdClient adClient;

    @PreAuthorize("hasRole('ROLE_AGENT')")
    @GetMapping(value="/ads", produces = "application/json")
    public ResponseEntity<?> getAllAds()  {
        // sada vraca sve oglase, nije implementirana pretraga
        return new ResponseEntity<>(adService.getAllAds(),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AGENT')")
    @PostMapping(value="/ads", produces = "application/json", consumes="application/json")
    public ResponseEntity<?> search(@RequestBody AdSearchDTO adSearchDTO)  {

        System.out.println("Location: " + adSearchDTO.getLocation());
        System.out.println("Start date: " + adSearchDTO.getStartDate());
        System.out.println("End date: " + adSearchDTO.getEndDate());
        System.out.println("Cdw: " + adSearchDTO.getCdw());
        System.out.println("Car brand: " + adSearchDTO.getCarBrand());
        System.out.println("Car model: " + adSearchDTO.getCarModel());
        System.out.println("Car class: " + adSearchDTO.getCarClass());
        System.out.println("Fuel type: " + adSearchDTO.getFuelType());
        System.out.println("Trasmission type:" + adSearchDTO.getTransmissionType());
        System.out.println("Children seats: " + adSearchDTO.getChildrenSeats());
        System.out.println("Mileage: " + adSearchDTO.getMileage());
        System.out.println("Planned mileage: " + adSearchDTO.getPlannedMileage());
        System.out.println("Min price: " + adSearchDTO.getMinPrice());
        System.out.println("Max price: " + adSearchDTO.getMaxPrice());
        String error = adService.validateAdDTO(adSearchDTO);
        if(error != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        return new ResponseEntity<>(adService.searchAds(adSearchDTO),HttpStatus.OK);
    }

    @PostMapping(value="/ad",consumes="application/json")
    public ResponseEntity<?> createNewAd(@Valid @RequestBody AdDTO newAdDTO)  {

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

        PostAdResponse adPosted= adClient.postAd(newAdDTO);

        if(adPosted.getIdAd() == 0){
            return new ResponseEntity<>("Ad was not been able to be posted on publishing app",HttpStatus.BAD_REQUEST);
        }

        newAdDTO.setId(adPosted.getIdAd());
        newAdDTO.getCar().setMainId(adPosted.getIdCar());
        newAdDTO.getCar().setAndroidToken(adPosted.getToken());
        newAdDTO.getPriceList().setMainId(adPosted.getIdPriceList());
        Advertisement ad = adService.createNewAd(newAdDTO);

        AdDTO returnAd = new AdDTO();
        returnAd.setId(ad.getId());
        returnAd.setCar(new CarDTO(ad.getCar()));
        returnAd.getCar().setAndroidToken(ad.getCar().getAndroidToken());

        return new ResponseEntity<>(returnAd,HttpStatus.CREATED);
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
