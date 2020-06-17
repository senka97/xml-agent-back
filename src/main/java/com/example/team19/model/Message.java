package com.example.team19.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="mainId")
    private Long mainId; //id poruke na glavnoj app
    @Column(name="content")
    private String content;
    @Column(name="dateTime")
    private LocalDateTime dateTime;
    @Column(name="fromUserId") //id usera na glavnoj
    private Long fromUserId;
    @Column(name="fromUserInfo")
    private String fromUserInfo;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Request request;

    public Message(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMainId() {
        return mainId;
    }

    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUserInfo() {
        return fromUserInfo;
    }

    public void setFromUserInfo(String fromUserInfo) {
        this.fromUserInfo = fromUserInfo;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
