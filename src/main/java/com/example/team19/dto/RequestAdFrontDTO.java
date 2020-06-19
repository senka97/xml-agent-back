package com.example.team19.dto;

import java.time.LocalDate;

public class RequestAdFrontDTO {

    private Long id;

    private Long mainId;

    private LocalDate startDate;

    private LocalDate endDate;

    private double payment;

    private double currentPricePerKm;

    private AdFrontDTO ad;

    private boolean reportCreated;

    public RequestAdFrontDTO(){

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

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public double getCurrentPricePerKm() {
        return currentPricePerKm;
    }

    public void setCurrentPricePerKm(double currentPricePerKm) {
        this.currentPricePerKm = currentPricePerKm;
    }

    public AdFrontDTO getAd() {
        return ad;
    }

    public void setAd(AdFrontDTO ad) {
        this.ad = ad;
    }

    public Long getMainId() {
        return mainId;
    }

    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }

    public boolean isReportCreated() {
        return reportCreated;
    }

    public void setReportCreated(boolean reportCreated) {
        this.reportCreated = reportCreated;
    }
}
