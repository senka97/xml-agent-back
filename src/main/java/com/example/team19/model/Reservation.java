package com.example.team19.model;

import com.example.team19.dto.ReservationDTO;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="startDate")
    private LocalDate startDate;
    @Column(name="endDate")
    private LocalDate endDate;
    @Column(name="clientFirstName")
    private String clientFirstName;
    @Column(name="clientLastName")
    private String clientLastName;
    @Column(name="clientEmail")
    private String clientEmail;
    @Column(name="clientPhoneNumber")
    private String clientPhoneNumber;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Advertisement advertisement;

    @Column(name="currentPricePerKm")
    private double currentPricePerKm; //ovo sam ubacila jer ima i na glavnoj
    @Column(name="payment")
    private double payment; //isto, racunace se ovde, a ne u glavnoj
    @Column(name="mainId")
    private Long mainId; //ovo je id ove rezervacije na glavnoj

    @OneToOne(mappedBy = "reservation")
    private Report report;

    @Column(name = "reportCreated")
    private Boolean reportCreated = false;


    public Reservation(){

    }

    public Reservation(ReservationDTO reservationDTO){
        this.clientFirstName = reservationDTO.getClientFirstName();
        this.clientLastName = reservationDTO.getClientLastName();
        this.clientEmail = reservationDTO.getClientEmail();
        this.clientPhoneNumber = reservationDTO.getClientPhoneNumber();
        this.startDate = reservationDTO.getStartDate();
        this.endDate = reservationDTO.getEndDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientPhoneNumber() {
        return clientPhoneNumber;
    }

    public void setClientPhoneNumber(String clientPhoneNumber) {
        this.clientPhoneNumber = clientPhoneNumber;
    }

    public Advertisement getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(Advertisement advertisement) {
        this.advertisement = advertisement;
    }

    public double getCurrentPricePerKm() {
        return currentPricePerKm;
    }

    public void setCurrentPricePerKm(double currentPricePerKm) {
        this.currentPricePerKm = currentPricePerKm;
    }

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public Long getMainId() {
        return mainId;
    }

    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Boolean getReportCreated() {
        return reportCreated;
    }

    public void setReportCreated(Boolean reportCreated) {
        this.reportCreated = reportCreated;
    }
}
