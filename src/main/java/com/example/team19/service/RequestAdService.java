package com.example.team19.service;

import com.example.team19.model.RequestAd;

public interface RequestAdService {

    RequestAd findById(Long id);
    RequestAd save(RequestAd requestAd);
}
