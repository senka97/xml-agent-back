package com.example.team19.model;

import javax.persistence.*;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="kilometers")
    private double kilometers;
    @Column(name="additionalInfo")
    private String additionalInfo;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Car car;

    private Report(){

    }


}
