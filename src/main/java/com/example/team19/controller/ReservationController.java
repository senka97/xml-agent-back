package com.example.team19.controller;

import com.example.team19.dto.AdDTO;
import com.example.team19.dto.ReservationDTO;
import com.example.team19.model.Advertisement;
import com.example.team19.model.Reservation;
import com.example.team19.service.impl.ReservationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin

public class ReservationController {

    @Autowired
    ReservationServiceImpl reservationService;

    @PostMapping(value="/reservations",consumes="application/json")
    public ResponseEntity<?> createNewReservation(@RequestBody ReservationDTO reservationDTO)  {

        if(reservationDTO.getClientFirstName() == null || reservationDTO.getClientLastName() == null || reservationDTO.getClientEmail() == null || reservationDTO.getClientPhoneNumber() == null){
            return new ResponseEntity<>("All information about client must be entered!", HttpStatus.BAD_REQUEST);
        }

        if(reservationDTO.getStartDate() == null || reservationDTO.getEndDate() == null){
            return new ResponseEntity<>("Both start and end date must be selected",HttpStatus.BAD_REQUEST);
        }

        if(reservationDTO.getEndDate().isBefore(reservationDTO.getStartDate())){
            return new ResponseEntity<>("Start date must be before end date!",HttpStatus.BAD_REQUEST);
        }

        Reservation r = reservationService.createNewReservation(reservationDTO);

        if(r == null)
        {
            return new ResponseEntity<>("These dates are already reserved",HttpStatus.EXPECTATION_FAILED);
        }
        else return new ResponseEntity<>(HttpStatus.CREATED);
    }
}

