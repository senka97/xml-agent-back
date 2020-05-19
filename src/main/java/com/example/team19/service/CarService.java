package com.example.team19.service;

import com.example.team19.dto.CarDTO;
import com.example.team19.dto.CarStatisticDTO;
import com.example.team19.model.Car;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface CarService {

    Car save(Car car);
    Car findById(Long id);
    ArrayList<CarDTO> getAllAvailableCars();
    Car createNewCar(CarDTO carDTO);
    CarStatisticDTO getCarWithMostKilometers();
    CarStatisticDTO getCarWithBestScore();
    CarStatisticDTO getCarWithMostComments();
    ArrayList<String> getCarPhotos(Long id);
}
