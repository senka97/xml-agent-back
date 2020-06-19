package com.example.team19.dto;

public class PriceListRequestDTO {

    private double pricePerKm;
    private double pricePerDay;
    private double priceForCdw;
    private int discount20Days;
    private int discount30Days;
    private String alias;

    public PriceListRequestDTO(){

    }

    public double getPricePerKm() {
        return pricePerKm;
    }

    public void setPricePerKm(double pricePerKm) {
        this.pricePerKm = pricePerKm;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public double getPriceForCdw() {
        return priceForCdw;
    }

    public void setPriceForCdw(double priceForCdw) {
        this.priceForCdw = priceForCdw;
    }

    public int getDiscount20Days() {
        return discount20Days;
    }

    public void setDiscount20Days(int discount20Days) {
        this.discount20Days = discount20Days;
    }

    public int getDiscount30Days() {
        return discount30Days;
    }

    public void setDiscount30Days(int discount30Days) {
        this.discount30Days = discount30Days;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
