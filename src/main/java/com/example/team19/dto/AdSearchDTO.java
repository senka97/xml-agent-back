package com.example.team19.dto;

import java.time.LocalDate;

public class AdSearchDTO {

    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private String cdw; //null ako se to ne pretrazuje
    private Long carBrand; //ako je null onda se ne pretrazuje po tome
    private Long carModel; //ako je null onda se ne pretrazuje po tome
    private String carClass;
    private String fuelType;
    private String transmissionType;
    private String childrenSeats; //null ili "" ako se po tom ne pretrazuje
    private String mileage; //null ili "" ako se ne pretrazuje po tome, 0 ako je nov auto
    private int plannedMileage; //0 ako se po tome ne pretrazuje
    private double minPrice; //ako je 0 onda se ne pretrazuje po tome
    private double maxPrice; //ako je 0 onda se ne pretrazuje po tome

    public AdSearchDTO(){
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getCdw() {
        return cdw;
    }

    public void setCdw(String cdw) {
        this.cdw = cdw;
    }

    public Long getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(Long carBrand) {
        this.carBrand = carBrand;
    }

    public Long getCarModel() {
        return carModel;
    }

    public void setCarModel(Long carModel) {
        this.carModel = carModel;
    }

    public String getCarClass() {
        return carClass;
    }

    public void setCarClass(String carClass) {
        this.carClass = carClass;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(String transmissionType) {
        this.transmissionType = transmissionType;
    }

    public String getChildrenSeats() {
        return childrenSeats;
    }

    public void setChildrenSeats(String childrenSeats) {
        this.childrenSeats = childrenSeats;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public int getPlannedMileage() {
        return plannedMileage;
    }

    public void setPlannedMileage(int plannedMileage) {
        this.plannedMileage = plannedMileage;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }
}
