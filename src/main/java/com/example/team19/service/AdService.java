package com.example.team19.service;

import com.example.team19.dto.AdDTO;
import com.example.team19.model.Advertisement;

import java.util.ArrayList;


public interface AdService {

    ArrayList<AdDTO> searchAds();
    Advertisement save(Advertisement ad);
    Advertisement createNewAd(AdDTO adDTO);
}
