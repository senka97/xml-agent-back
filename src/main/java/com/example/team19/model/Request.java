package com.example.team19.model;

import com.example.team19.enums.RequestStatus;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, fetch= FetchType.LAZY)
    private Set<RequestAd> requestAds;
    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Message> messages;

    @Column(name="clientFirstName") //umesto svega ovoga na glavnoj mi je samo clientId
    private String clientFirstName;
    @Column(name="clientLastName")
    private String clientLastName;
    @Column(name="clientPhoneNumber")
    private String clientPhoneNumber; //dovde; na glavnoj jos imam i ownerID
    @Column(name="clientEmail")
    private String clientEmail;

    @Column(name="mainId")
    private Long mainId; //ovo je id requesta na glavnoj

    //nisam stavila clientID i ownerID jer ne znam da li mi je to potrebno ovde za sada

    public Request(){
        this.requestAds = new HashSet<>();
        this.messages = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public Set<RequestAd> getRequestAds() {
        return requestAds;
    }

    public void setRequestAds(Set<RequestAd> requestAds) {
        this.requestAds = requestAds;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public Long getMainId() {
        return mainId;
    }

    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }
}
