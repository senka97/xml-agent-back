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
    @Column(name="alias")
    private String alias;
    @OneToMany(mappedBy = "priceList", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Advertisement> advertisements;

    public PriceList(){

    }
}
