package com.example.team19.controller;

import com.example.team19.soap.CarClient;
import com.example.team19.wsdl.CommentsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/soap")
public class SoapController {

    @Autowired
    private CarClient carClient;

    @GetMapping(value = "/comments/{adId}")
    public ResponseEntity<?> getComments(@PathVariable("adId") Long adId){

        CommentsResponse cr = this.carClient.getComments(adId);
        return new ResponseEntity(cr.getCommentSOAP(), HttpStatus.OK);
    }
}
