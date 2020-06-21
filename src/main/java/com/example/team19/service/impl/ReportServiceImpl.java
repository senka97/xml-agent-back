package com.example.team19.service.impl;

import com.example.team19.dto.ReportDTO;
import com.example.team19.model.*;
import com.example.team19.repository.ReportRepository;
import com.example.team19.service.*;
import com.example.team19.soap.RentClient;
import com.example.team19.wsdl.CreateRequestReportResponse;
import com.example.team19.wsdl.CreateReservationReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private RequestAdService requestAdService;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private AdService adService;

    @Autowired
    private CarService carService;

    @Autowired
    private RentClient rentClient;

    @Override
    public Boolean createRequestReport(Long requestAdId, String content, double km) {

        RequestAd requestAd = requestAdService.findById(requestAdId);

        if(requestAd != null && !requestAd.getReportCreated())
        {

            Advertisement ad = this.adService.findById(requestAd.getAdvertisement().getId());

            if( ad != null)
            {
                Car car = this.carService.findById(ad.getCar().getId());

                if( car != null) {
                    // slanje na glavnu aplikaciju, tek kada se tamo sacuva cuva se i ovde
                    CreateRequestReportResponse response = this.rentClient.createRequestReport(requestAd.getMainId(), content, km);
                    System.out.println("*********************************");
                    System.out.println("Vratio se sa glavne aplikacije");
                    if(response.isReportCreated())
                    {
                        System.out.println("izvestaj je kreiran");
                        Report r = new Report();
                        r.setContent(content);
                        r.setKm(km);
                        r.setRequestAd(requestAd);
                        r.setReservation(null);
                        requestAd.setReportCreated(true);
                        car.setMileage(car.getMileage() + km);

                        requestAdService.save(requestAd);
                        carService.save(car);
                        reportRepository.save(r);
                        return true;
                    }
                    else return false;
                }
                else return false;
            }
            else return false;
        }
        else return false;

    }

    @Override
    public Boolean createReservationReport(Long reservationId, String content, double km) {

        Reservation reservation = this.reservationService.findById(reservationId);

        if(reservation != null && !reservation.getReportCreated())
        {

            Advertisement ad = this.adService.findById(reservation.getAdvertisement().getId());

            if( ad != null)
            {
                Car car = this.carService.findById(ad.getCar().getId());
                if( car != null) {
                    // slanje na glavnu aplikaciju, tek kada se tamo sacuva cuva se i ovde
                    CreateReservationReportResponse response = this.rentClient.createReservationReport(reservation.getMainId(), content, km);
                    if(response.isReportCreated())
                    {
                        Report r = new Report();
                        r.setContent(content);
                        r.setKm(km);
                        r.setRequestAd(null);
                        r.setReservation(reservation);
                        reservation.setReportCreated(true);
                        car.setMileage(car.getMileage() + km);

                        reservationService.save(reservation);
                        carService.save(car);
                        reportRepository.save(r);
                        return true;
                    }
                    else return false;
                }
                else return false;
            }
            else return false;
        }
        else return false;

    }

    @Override
    public ReportDTO showRequestReport(Long id) {

        Report report = this.reportRepository.findByRequestAdId(id);

        if(report != null)
        {
            ReportDTO r = new ReportDTO();
            r.setId(report.getId());
            r.setKm(report.getKm());
            r.setContent(report.getContent());

            return r;
        }
        else return null;
    }

    @Override
    public ReportDTO showReservationReport(Long id) {

        Report report = this.reportRepository.findByReservationId(id);

        if(report != null)
        {
            ReportDTO r = new ReportDTO();
            r.setId(report.getId());
            r.setKm(report.getKm());
            r.setContent(report.getContent());

            return r;
        }
        else return null;
    }
}
