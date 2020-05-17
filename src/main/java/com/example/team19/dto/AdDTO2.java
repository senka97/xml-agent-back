package com.example.team19.dto;

import java.time.LocalDate;

public class AdDTO2 {
// u ovom DTO datumi su String zbog prikaza na frontu
    private Long id;

    private String startDate;

    private String endDate;

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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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
