package com.example.team19.service.impl;

import com.example.team19.model.CarBrand;
import com.example.team19.repository.CarBrandRepository;
import com.example.team19.service.CarBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarBrandServiceImpl implements CarBrandService {

    @Autowired
    private CarBrandRepository carBrandRepository;

    @Override
    public CarBrand findByName(String name) {
        return carBrandRepository.findByName(name);
    }
}
