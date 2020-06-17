package com.example.team19.controller;

import com.example.team19.dto.ReservationDTO;
import com.example.team19.dto.ReservationFrontDTO;
import com.example.team19.dto.ReservationResponseDTO;
import com.example.team19.model.Advertisement;
import com.example.team19.service.impl.AdServiceImpl;
import com.example.team19.service.impl.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin

public class ReservationController {

    @Autowired
    ReservationServiceImpl reservationService;
    @Autowired
    AdServiceImpl adService;

    @PostMapping(value="/reservations",consumes="application/json")
    public ResponseEntity<?> createNewReservation(@RequestBody ReservationDTO reservationDTO)  {

        //Prvo proverim da li postoji oglas sa tim id
        Advertisement ad = adService.findById(reservationDTO.getAdId());
        if(ad == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ad with that id doesn't exist.");
        }

        if(reservationDTO.getClientFirstName() == null || reservationDTO.getClientLastName() == null || reservationDTO.getClientEmail() == null || reservationDTO.getClientPhoneNumber() == null){
            return new ResponseEntity<>("All information about client must be entered!", HttpStatus.BAD_REQUEST);
        }

        if(reservationDTO.getStartDate() == null || reservationDTO.getEndDate() == null){
            return new ResponseEntity<>("Both start and end date must be selected",HttpStatus.BAD_REQUEST);
        }

        if(reservationDTO.getEndDate().isBefore(reservationDTO.getStartDate())){
            return new ResponseEntity<>("Start date must be before end date!",HttpStatus.BAD_REQUEST);
        }

        ReservationResponseDTO r = reservationService.createNewReservation(reservationDTO);

        if(r == null) {
            return new ResponseEntity<>("These dates are already reserved.",HttpStatus.EXPECTATION_FAILED);
        } else {
            if(r.getId() == null){ //napravljen prazan
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong on the main app. Please contact technical support.");
            }
            return new ResponseEntity<>(r, HttpStatus.CREATED);
        }
    }

    @GetMapping(value="/reservations")
    public ResponseEntity<?> getReservationsFront(){

        List<ReservationFrontDTO> reservationFrontDTOs = this.reservationService.getReservationsFront();
        return new ResponseEntity(reservationFrontDTOs,HttpStatus.OK);
    }
}

