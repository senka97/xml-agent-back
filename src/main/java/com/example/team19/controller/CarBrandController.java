package com.example.team19.controller;

import com.example.team19.service.impl.CarBrandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/carBrand")
@CrossOrigin
public class CarBrandController {

     @Autowired
    CarBrandServiceImpl carBrandService;

    @PreAuthorize("hasRole('ROLE_AGENT')")
    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getAll()  {
        return new ResponseEntity<>(carBrandService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_AGENT')")
    @GetMapping(value="/{id}/carModel", produces = "application/json")
    public ResponseEntity<?> getAll(@PathVariable("id") Long id)  {
        return new ResponseEntity<>(carBrandService.findModels(id), HttpStatus.OK);
    }
}
