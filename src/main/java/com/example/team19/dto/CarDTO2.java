package com.example.team19.dto;

import com.example.team19.enums.CarClass;
import com.example.team19.enums.FuelType;
import com.example.team19.enums.TransmissionType;

import java.util.ArrayList;
import java.util.Set;

public class CarDTO2 {
// u ovom DTO enumi su string zbog prikaza na frontu
    private Long id;

    private int childrenSeats;

    private double rate;

    private double mileage;

    private boolean hasAndroidApp;

    private CarBrandDTO carBrand;

    private CarModelDTO carModel;

    private String carClass;

    private String transType;

    private String fuelType;

    private ArrayList<String> photos64 = new ArrayList<>();


    public CarDTO2(){

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

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public CarBrandDTO getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(CarBrandDTO carBrand) {
        this.carBrand = carBrand;
    }

    public CarModelDTO getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModelDTO carModel) {
        this.carModel = carModel;
    }

    public String getCarClass() {
        return carClass;
    }

    public void setCarClass(String carClass) {
        this.carClass = carClass;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public boolean getHasAndroidApp() {
        return hasAndroidApp;
    }

    public void setHasAndroidApp(boolean hasAndroidApp) {
        this.hasAndroidApp = hasAndroidApp;
    }

    public ArrayList<String> getPhotos64() {
        return photos64;
    }

    public void setPhotos64(ArrayList<String> photos64) {
        this.photos64 = photos64;
    }
}
