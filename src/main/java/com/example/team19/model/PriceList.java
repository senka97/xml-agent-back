package com.example.team19.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class PriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="pricePerKm")
    private double pricePerKm;
    @Column(name="pricePerDay")
    private double pricePerDay;
    @Column(name="discount20Days")
    private int discount20Days;
    @Column(name="discount30Days")
    private int discount30Days;
    @Column(name="alias", unique = true)
    private String alias;
    @OneToMany(mappedBy = "priceList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Advertisement> advertisements;
    @Column(name="mainId")
    private Long mainId;

    public PriceList(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Set<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(Set<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    public Long getMainId() {
        return mainId;
    }

    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }
}
