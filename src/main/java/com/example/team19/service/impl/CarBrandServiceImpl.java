package com.example.team19.service.impl;

import com.example.team19.dto.CarBrandDTO;
import com.example.team19.dto.CarBrandDTOSearch;
import com.example.team19.dto.CarModelDTOSearch;
import com.example.team19.model.Car;
import com.example.team19.model.CarBrand;
import com.example.team19.model.CarModel;
import com.example.team19.repository.CarBrandRepository;
import com.example.team19.service.CarBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarBrandServiceImpl implements CarBrandService {

    @Autowired
    private CarBrandRepository carBrandRepository;

    @Override
    public CarBrand findByName(String name) {
        return carBrandRepository.findByName(name);
    }

    @Override
    public List<CarBrandDTOSearch> findAll() {

        List<CarBrand> carBrands = carBrandRepository.findAll();
        List<CarBrandDTOSearch> carBrandDTO = new ArrayList<>();
        for(CarBrand carBrand: carBrands){
            carBrandDTO.add(new CarBrandDTOSearch(carBrand));
        }
        return carBrandDTO;
    }

    @Override
    public List<CarModelDTOSearch> findModels(Long id) {

        List<CarModelDTOSearch> carModelDTO = new ArrayList<>();
        CarBrand carBrand = carBrandRepository.findById(id).orElse(null);
        if(carBrand != null){
            for(CarModel carModel: carBrand.getCarModels()){
                carModelDTO.add(new CarModelDTOSearch(carModel));
            }
            return carModelDTO;
        }else{
            return null;
        }
    }
}
