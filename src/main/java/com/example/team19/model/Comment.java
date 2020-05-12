package com.example.team19.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="fromComment")
    private String fromComment;
    @Column(name="content")
    private String content;
    @Column(name="dateTime")
    private LocalDateTime dateTime;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Car car;

    public Comment(){

    }

}
