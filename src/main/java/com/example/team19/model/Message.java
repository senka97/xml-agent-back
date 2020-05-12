package com.example.team19.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="content")
    private String content;
    @Column(name="dateTime")
    private LocalDateTime dateTime;
    @Column(name="fromMsg")
    private String fromMsg;
    @Column(name="toMsg")
    private String toMsg;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Request request;

    public Message(){

    }

}
