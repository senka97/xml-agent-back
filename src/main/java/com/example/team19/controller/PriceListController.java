package com.example.team19.controller;


import com.example.team19.dto.PriceListDTO;
import com.example.team19.dto.PriceListRequestDTO;
import com.example.team19.model.PriceList;
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
    public ResponseEntity<?> createPriceList(@RequestBody PriceListRequestDTO priceListRequestDTO){

        /*if(priceList.getDiscount20Days() < 0 || priceList.getDiscount30Days() < 0 || priceList.getPricePerDay() < 0 || priceList.getPricePerKm() < 0 || priceList.getAlias() == null) {
            return new ResponseEntity<>("Invalid data sent", HttpStatus.BAD_REQUEST);
        }
        if(priceListService.createPriceList(priceList) != null) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else{
            return new ResponseEntity<>("Price list alias already exists", HttpStatus.BAD_REQUEST);
        }*/

        if(priceListRequestDTO.getAlias() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Alias for price list is mandatory.");
        }else{
            if(priceListRequestDTO.getAlias().equals("")){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Alias for price list is mandatory.");
            }
        }
        if(priceListRequestDTO.getPricePerKm() <=0 || priceListRequestDTO.getPricePerDay()<=0 || priceListRequestDTO.getPriceForCdw()<=0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Prices have to be greater that 0.");
        }
        if(priceListRequestDTO.getDiscount30Days()<0 || priceListRequestDTO.getDiscount20Days()<0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Discount has to be positive integer.");
        }

        PriceList exist = this.priceListService.findByAlias(priceListRequestDTO.getAlias());

        if(exist != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You already have a price list with that alias.");
        }

        PriceListDTO priceListResponseDTO = this.priceListService.createPriceList(priceListRequestDTO);
        if(priceListResponseDTO.getMainId() == 0){ //ako je mainId 0, desila se greska na glavnoj app, u alias stavim poruku sa glavne aplikacije
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(priceListRequestDTO.getAlias());
        }
        return new ResponseEntity(priceListResponseDTO,HttpStatus.CREATED);
    }


    @DeleteMapping(value="priceLists/{id}")
    @PreAuthorize("hasRole('ROLE_AGENT')")
    public ResponseEntity<?> deletePriceList(@PathVariable("id") Long id)
    {
        /*if(priceListService.deletePriceList(id)) {
            return  new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
        } else {
            return new ResponseEntity<>("This price list is active in some of your ads",HttpStatus.BAD_REQUEST);
        }*/
        if(id <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id is not valid. It has to be positive number.");
        }
        PriceList priceList = this.priceListService.findById(id);
        if(priceList == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Price list with that id doesn't exist.");
        }else{
            if(priceList.isRemoved()){
                return ResponseEntity.status(HttpStatus.GONE).body("Price list with that id doesn't exist anymore.");
            }
        }

        String msg = this.priceListService.deletePriceList(id);
        if(msg == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg);
        }
    }
}
