package com.example.team19.service;

import com.example.team19.dto.AdDTO;
import com.example.team19.dto.AdDTO2;
import com.example.team19.dto.AdSearchDTO;
import com.example.team19.model.Advertisement;

import java.util.ArrayList;


public interface AdService {

    ArrayList<AdDTO2> getAllAds();
    ArrayList<AdDTO2> searchAds(AdSearchDTO adSearchDTO);
    String validateAdDTO(AdSearchDTO adSearchDTO);
    Advertisement save(Advertisement ad);
    Advertisement createNewAd(AdDTO adDTO);
    AdDTO2 getAd(Long id);
    Advertisement findById(Long id);
    ArrayList<Advertisement> findActiveAds();
}
