package com.example.team19.controller;


import com.example.team19.dto.PriceListDTO;
import com.example.team19.service.impl.PriceListServiceImpl;
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
public class PriceListController {

    @Autowired
    private PriceListServiceImpl priceListService;

    @GetMapping(value="/priceList", produces = "application/json")
    public ArrayList<PriceListDTO> search()  {
        return priceListService.getAll();
    }

    @PreAuthorize("hasRole('ROLE_AGENT')")
    @GetMapping(value="/priceLists", produces = "application/json")
    public ResponseEntity<?> getAll()  {

        return new ResponseEntity<>(priceListService.getAll(), HttpStatus.OK);
    }

    @PostMapping(value="/priceLists", produces = "application/json")
    @PreAuthorize("hasRole('ROLE_AGENT')")
    public ResponseEntity<?> createPriceList(@RequestBody PriceListDTO priceList){

        if(priceList.getDiscount20Days() < 0 || priceList.getDiscount30Days() < 0 || priceList.getPricePerDay() < 0 || priceList.getPricePerKm() < 0 || priceList.getAlias() == null)
        {
            return new ResponseEntity<>("Invalid data sent", HttpStatus.BAD_REQUEST);
        }

        if(priceListService.createPriceList(priceList) != null)
        {
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        else return new ResponseEntity<>("Price list alias already exists", HttpStatus.BAD_REQUEST);
    }


    @DeleteMapping(value="priceLists/{id}")
    @PreAuthorize("hasRole('ROLE_AGENT')")
    public ResponseEntity<?> deletePriceList(@PathVariable("id") Long id)
    {
        if(priceListService.deletePriceList(id))
        {
            return  new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
        }
        else return new ResponseEntity<>("This price list is active in some of your ads",HttpStatus.BAD_REQUEST);
    }
}
