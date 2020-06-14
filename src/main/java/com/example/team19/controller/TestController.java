package com.example.team19.controller;

import com.example.team19.soap.TestClient;
import com.example.team19.wsdl.GetTestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/test")
public class TestController {

    @Autowired
    private TestClient testClient;

    @GetMapping
    public ResponseEntity<?> test() {

        GetTestResponse gtr = this.testClient.getTest("Test");
        return new ResponseEntity(gtr.getResponse(), HttpStatus.OK);

    }

}
