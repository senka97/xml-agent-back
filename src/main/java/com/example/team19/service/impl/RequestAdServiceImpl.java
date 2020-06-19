package com.example.team19.service.impl;

import com.example.team19.model.RequestAd;
import com.example.team19.repository.RequestAdRepository;
import com.example.team19.service.RequestAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestAdServiceImpl implements RequestAdService {

    @Autowired
    private RequestAdRepository requestAdRepository;

    @Override
    public RequestAd findById(Long id) {

        return this.requestAdRepository.findById(id).orElse(null);
    }

    @Override
    public RequestAd save(RequestAd requestAd) {

        return  this.requestAdRepository.save(requestAd);
    }


}
