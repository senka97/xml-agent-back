package com.example.team19.service;

import com.example.team19.dto.CarBrandDTO;
import com.example.team19.dto.CarBrandDTOSearch;
import com.example.team19.dto.CarModelDTOSearch;
import com.example.team19.model.CarBrand;

import java.util.List;

public interface CarBrandService {

    CarBrand findByName(String name);

    List<CarBrandDTOSearch> findAll();
    List<CarModelDTOSearch> findModels(Long id);
}
