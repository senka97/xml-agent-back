package com.example.team19.service.impl;

import com.example.team19.dto.CarDTO;
import com.example.team19.model.Advertisement;
import com.example.team19.model.Car;
import com.example.team19.repository.CarRepository;
import com.example.team19.service.CarModelService;
import com.example.team19.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarModelService carModelService;

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car findById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    @Override
    public ArrayList<CarDTO> getAllAvailableCars() {
        ArrayList<CarDTO> carsDTO = new ArrayList<>();
        List<Car> cars = carRepository.findAll();
        LocalDate now =LocalDate.now();

        for(Car car: cars){
            boolean hasActiveAd = false;
            if(car.getAdvertisements() != null)
                for(Advertisement ad : car.getAdvertisements()){
                    if(ad.getEndDate() != null) {
                        if (ad.getEndDate().isAfter(now) || ad.getEndDate().equals(now)) { //TODO videti jos sta znaci da je oglas aktivan
                            hasActiveAd = true;
                            break;
                        }
                    }
                }

            if(!hasActiveAd){
                CarDTO carDTO = new CarDTO(car);
                carsDTO.add(carDTO);
            }
        }


        return carsDTO;
    }

    @Override
    public Car createNewCar(CarDTO carDTO) {

        Car car = new Car();
        car.setCarModel(carModelService.findById(carDTO.getCarModel().getId()));
        car.setCarClass(carDTO.getCarClass());
        car.setChildrenSeats(carDTO.getChildrenSeats());
        car.setFuelType(carDTO.getFuelType());
        car.setTransType(carDTO.getTransType());
        car.setHasAndroidApp(carDTO.getHasAndroidApp());
        //TODO Dodavanje Slika
        //car.setPhotos();
        Car newCar = save(car);

        return newCar;
    }


}
