package com.example.team19.controller;

import com.example.team19.dto.AdDTO;
import com.example.team19.service.impl.AdServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateParsingException;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class AdController {

    @Autowired
    private AdServiceImpl adService;

    @GetMapping(value="/ads", produces = "application/json")
    public ArrayList<AdDTO> search()  {
        // sada vraca sve oglase, nije implementirana pretraga
        return adService.searchAds();
    }

}
