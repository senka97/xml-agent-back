package com.example.team19.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="startDate")
    private LocalDate startDate;
    @Column(name="endDate")
    private LocalDate endDate;
    @Column(name="limitKm")
    private int limitKm;
    @Column(name="cdw")
    private boolean cdw;
    @Column(name="location")
    private String location;
    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RequestAd> requestAds;
    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Reservation> reservations;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PriceList priceList;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Car car;
    @Column(name="mainId")
    private Long mainId;

    public Advertisement(){

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

    public boolean getCdw() {
        return cdw;
    }

    public void setCdw(boolean cdw) {
        this.cdw = cdw;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public PriceList getPriceList() {
        return priceList;
    }

    public void setPriceList(PriceList priceList) {
        this.priceList = priceList;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getMainId() {
        return mainId;
    }

    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }
}
