package com.example.team19.model;

import javax.persistence.*;
import java.util.Set;


@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="status")
    private boolean status;
    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, fetch= FetchType.LAZY)
    private Set<RequestAd> requestAds;
    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Message> messages;

    private Request(){

    }





}
