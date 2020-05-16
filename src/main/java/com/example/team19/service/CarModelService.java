package com.example.team19.service;

import com.example.team19.dto.CarModelDTO;
import com.example.team19.model.CarModel;

import java.util.ArrayList;

public interface CarModelService {

    CarModel findByName(String name);
    CarModel findById(Long id);
    ArrayList<CarModelDTO> getAll();
}
