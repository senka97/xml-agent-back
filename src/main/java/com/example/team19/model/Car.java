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
    private Set<Report> reports;
    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comment> comments;

    public Car(){

    }



}
