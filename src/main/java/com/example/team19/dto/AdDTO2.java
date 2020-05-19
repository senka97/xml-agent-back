package com.example.team19.dto;

import java.time.LocalDate;

public class AdDTO2 {
// CarDTO2 je razlika
    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private int limitKm;

    private boolean cdw;

    private String location;

    private PriceListDTO priceList;

    private CarDTO2 car;

    public AdDTO2(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getLimitKm() {
        return limitKm;
    }

    public void setLimitKm(int limitKm) {
        this.limitKm = limitKm;
    }

    public boolean isCdw() {
        return cdw;
    }

    public void setCdw(boolean cdw) {
        this.cdw = cdw;
    }

    public PriceListDTO getPriceList() {
        return priceList;
    }

    public void setPriceList(PriceListDTO priceList) {
        this.priceList = priceList;
    }

    public CarDTO2 getCar() {
        return car;
    }

    public void setCar(CarDTO2 car) {
        this.car = car;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
