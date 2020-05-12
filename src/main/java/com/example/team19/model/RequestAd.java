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
    @Column(name="clientFirstName")
    private String clientFirstName;
    @Column(name="clientLastName")
    private String clientLastName;
    @Column(name="clientPhoneNumber")
    private String clientPhoneNumber;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Advertisement advertisement;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Request request;

    private RequestAd(){

    }


}
