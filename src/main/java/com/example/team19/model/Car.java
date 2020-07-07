package com.example.team19.model;

import com.example.team19.enums.CarClass;
import com.example.team19.enums.FuelType;
import com.example.team19.enums.TransmissionType;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="childrenSeats")
    private int childrenSeats;
    @Column(name="rate")
    private double rate;
    @Column(name="mileage")
    private double mileage;
    @Column(name="hasAndroidApp")
    private boolean hasAndroidApp;
    @Column(name="android_token")
    private String androidToken;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CarModel carModel;
    @Enumerated(EnumType.STRING)
    private CarClass carClass;
    @Enumerated(EnumType.STRING)
    private TransmissionType transType;
    @Enumerated(EnumType.STRING)
    private FuelType fuelType;
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Advertisement> advertisements;
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Photo> photos;
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comment> comments;
    @Column(name="mainId")
    private Long mainId;


    public Car(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getChildrenSeats() {
        return childrenSeats;
    }

    public void setChildrenSeats(int childrenSeats) {
        this.childrenSeats = childrenSeats;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public CarModel getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModel carModel) {
        this.carModel = carModel;
    }

    public CarClass getCarClass() {
        return carClass;
    }

    public void setCarClass(CarClass carClass) {
        this.carClass = carClass;
    }

    public TransmissionType getTransType() {
        return transType;
    }

    public void setTransType(TransmissionType transType) {
        this.transType = transType;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public Set<Advertisement> getAdvertisements() {
        return advertisements;
    }

    public void setAdvertisements(Set<Advertisement> advertisements) {
        this.advertisements = advertisements;
    }

    public boolean getHasAndroidApp() {
        return hasAndroidApp;
    }

    public void setHasAndroidApp(boolean hasAndroidApp) {
        this.hasAndroidApp = hasAndroidApp;
    }

    public Long getMainId() {
        return mainId;
    }

    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public boolean isHasAndroidApp() {
        return hasAndroidApp;
    }

    public String getAndroidToken() {
        return androidToken;
    }

    public void setAndroidToken(String androidToken) {
        this.androidToken = androidToken;
    }
}
