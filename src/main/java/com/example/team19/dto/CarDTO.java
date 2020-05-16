package com.example.team19.dto;

import com.example.team19.enums.CarClass;
import com.example.team19.enums.FuelType;
import com.example.team19.enums.TransmissionType;
import com.example.team19.model.*;

import javax.persistence.*;
import java.util.Set;

public class CarDTO {

    private Long id;

    private int childrenSeats;

    private double rate;

    private double mileage;

    private CarBrandDTO carBrand;

    private CarModelDTO carModel;

    private CarClass carClass;

    private TransmissionType transType;

    private FuelType fuelType;

    private Set<PhotoDTO> photos;


    public CarDTO(){

    }

    public CarDTO(Car car){
        id = car.getId();
        childrenSeats = car.getChildrenSeats();
        rate = car.getRate();
        mileage = car.getMileage();
        carBrand = new CarBrandDTO();
        carModel = new CarModelDTO(car.getCarModel());
        carClass = car.getCarClass();
        transType = car.getTransType();
        fuelType = car.getFuelType();

        //TODO Slike
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getChildrenSeats() {
        return childrenSeats;
    }

    public void setChildrenSeats(int childrenSeats) {
        this.childrenSeats = childrenSeats;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public CarModelDTO getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModelDTO carModel) {
        this.carModel = carModel;
    }

    public CarClass getCarClass() {
        return carClass;
    }

    public void setCarClass(CarClass carClass) {
        this.carClass = carClass;
    }

    public TransmissionType getTransType() {
        return transType;
    }

    public void setTransType(TransmissionType transType) {
        this.transType = transType;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Set<PhotoDTO> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<PhotoDTO> photos) {
        this.photos = photos;
    }

    public CarBrandDTO getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(CarBrandDTO carBrand) {
        this.carBrand = carBrand;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }
}
