package com.example.team19.service.impl;

import com.example.team19.dto.CarBrandDTO;
import com.example.team19.dto.CarModelDTO;
import com.example.team19.model.CarModel;
import com.example.team19.repository.CarModelRepository;
import com.example.team19.service.CarModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CarModelServiceImpl implements CarModelService {

    @Autowired
    private CarModelRepository carModelRepository;

    @Override
    public CarModel findByName(String name) {
        return carModelRepository.findByName(name);
    }

    @Override
    public CarModel findById(Long id) {
        return carModelRepository.findById(id).orElse(null);
    }

    @Override
    public ArrayList<CarModelDTO> getAll() {
        List<CarModel> models = carModelRepository.findAll();
        ArrayList<CarModelDTO> modelsDTO = new ArrayList<>();

        for(CarModel carModel: models){
            CarModelDTO carModelDTO = new CarModelDTO();
            carModelDTO.setId(carModel.getId());
            carModelDTO.setName(carModel.getName());

            CarBrandDTO carBrandDTO = new CarBrandDTO();
            carBrandDTO.setId(carModel.getCarBrand().getId());
            carBrandDTO.setName(carModel.getCarBrand().getName());

            carModelDTO.setCarBrand(carBrandDTO);

            modelsDTO.add(carModelDTO);
        }

        return modelsDTO;
    }
}
