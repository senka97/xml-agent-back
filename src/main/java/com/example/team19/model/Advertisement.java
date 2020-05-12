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
    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RequestAd> requestAds;
    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Reservation> reservations;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PriceList priceList;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Car car;

    public Advertisement(){

    }


}
