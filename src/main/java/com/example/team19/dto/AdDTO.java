package com.example.team19.dto;

import com.example.team19.model.Car;
import com.example.team19.model.PriceList;
import com.example.team19.model.RequestAd;
import com.example.team19.model.Reservation;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

public class AdDTO {

    private Long id;

    private LocalDate startDate;

    private LocalDate endDate;

    private int limitKm;

    private boolean cdw;

    private PriceListDTO priceList;

    private CarDTO car;

    public AdDTO(){

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

    public void setPriceList(PriceListDTO priceListDTO) {
        this.priceList = priceListDTO;
    }

    public CarDTO getCar() {
        return car;
    }

    public void setCar(CarDTO carDTO) {
        this.car = carDTO;
    }
}
