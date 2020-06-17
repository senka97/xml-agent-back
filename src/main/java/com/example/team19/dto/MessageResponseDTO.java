package com.example.team19.dto;

import com.example.team19.model.Message;

import java.time.LocalDateTime;

public class MessageResponseDTO {

    private Long id;
    private Long mainId;
    private Long fromUserId;
    private String fromUserInfo;
    private String content;
    private LocalDateTime dateTime;

    public MessageResponseDTO(){

    }

    public MessageResponseDTO(Message m){
        this.id = m.getId();
        this.mainId = m.getMainId();
        this.fromUserId = m.getFromUserId();
        this.fromUserInfo = m.getFromUserInfo();
        this.content = m.getContent();
        this.dateTime = m.getDateTime();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getMainId() {
        return mainId;
    }

    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }
}
