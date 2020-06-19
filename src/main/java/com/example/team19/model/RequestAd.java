package com.example.team19.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class RequestAd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="startDate")
    private LocalDate startDate;
    @Column(name="endDate")
    private LocalDate endDate;
    @Column(name="currentPricePerKm")
    private double currentPricePerKm;


    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Advertisement advertisement;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Request request;

    @Column(name="payment")
    private double payment;

    @Column(name="mainId")
    private Long mainId;

    @OneToOne(mappedBy = "requestAd")
    private Report report;

    @Column(name = "reportCreated")
    private Boolean reportCreated = false;

    public RequestAd(){

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

    public double getCurrentPricePerKm() {
        return currentPricePerKm;
    }

    public void setCurrentPricePerKm(double currentPricePerKm) {
        this.currentPricePerKm = currentPricePerKm;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
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

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Boolean getReportCreated() {
        return reportCreated;
    }

    public void setReportCreated(Boolean reportCreated) {
        this.reportCreated = reportCreated;
    }
}
