package com.example.team19.dto;

import java.util.ArrayList;
import java.util.List;

public class RequestFrontDTO {

    private Long id;

    private Long mainId;

    private String status;

    private String clientName;

    private String clientLastName;

    private String clientPhoneNumber;

    private String clientEmail;

    private List<RequestAdFrontDTO> requestAds = new ArrayList<>();

    public RequestFrontDTO(){

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }

    public List<RequestAdFrontDTO> getRequestAds() {
        return requestAds;
    }

    public void setRequestAds(List<RequestAdFrontDTO> requestAds) {
        this.requestAds = requestAds;
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
