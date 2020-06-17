package com.example.team19.dto;

import com.example.team19.model.Advertisement;
import com.example.team19.model.Reservation;

import javax.persistence.*;
import java.time.LocalDate;

public class ReservationResponseDTO {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private String clientFirstName;
    private String clientLastName;
    private String clientEmail;
    private String clientPhoneNumber;
    private Long adId;
    private double currentPricePerKm;
    private double payment;
    private Long mainId;

    public ReservationResponseDTO(){

    }

    public ReservationResponseDTO(Reservation reservation){
        this.id = reservation.getId();
        this.startDate = reservation.getStartDate();
        this.endDate = reservation.getEndDate();
        this.clientFirstName = reservation.getClientFirstName();
        this.clientLastName = reservation.getClientLastName();
        this.clientEmail = reservation.getClientEmail();
        this.clientPhoneNumber = reservation.getClientPhoneNumber();
        this.adId = reservation.getAdvertisement().getId();
        this.currentPricePerKm = reservation.getCurrentPricePerKm();
        this.payment = reservation.getPayment();
        this.mainId = reservation.getMainId();
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

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public double getCurrentPricePerKm() {
        return currentPricePerKm;
    }

    public void setCurrentPricePerKm(double currentPricePerKm) {
        this.currentPricePerKm = currentPricePerKm;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public Long getMainId() {
        return mainId;
    }

    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }
}
