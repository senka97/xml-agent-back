package com.example.team19.controller;

import com.example.team19.dto.RequestFrontDTO;
import com.example.team19.model.Request;
import com.example.team19.service.impl.RequestServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/request")
@CrossOrigin
public class RequestController {

    @Autowired
    private RequestServiceImpl requestService;


    @GetMapping(value="/pending", produces="application/properties")
    public ResponseEntity<?> getPendingRequestsFront(){

        List<RequestFrontDTO> requestFrontDTOs = this.requestService.getPendingRequestsFront();
        return new ResponseEntity(requestFrontDTOs, HttpStatus.OK);
    }

    @PutMapping(value="/accept/{id}")
    public ResponseEntity<?> acceptPendingRequest(@PathVariable("id") Long id){

        if(id <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id has to be positive long number");
        }
        Request r = this.requestService.findById(id);
        if(r == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request with that id doesn't exist.");
        }else{
            String msg = this.requestService.acceptPendingRequest(id);
            if(msg == null){
                return ResponseEntity.status(HttpStatus.OK).build();
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
            }
        }

    }

    @PutMapping(value="/reject/{id}")
    public ResponseEntity<?> rejectPendingRequest(@PathVariable("id") Long id){

        if(id <= 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id has to be positive long number");
        }
        Request r = this.requestService.findById(id);
        if(r == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request with that id doesn't exist.");
        }else{
            String msg = this.requestService.rejectPendingRequest(id);
            if(msg == null){
                return ResponseEntity.status(HttpStatus.OK).build();
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(msg);
            }
        }

    }
}
