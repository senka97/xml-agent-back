package com.example.team19.controller;

import com.example.team19.dto.ReportDTO;
import com.example.team19.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/reports", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping(value = "/request",consumes = "application/json")
    public ResponseEntity<?> createRequestReport(@Valid @RequestBody ReportDTO report){

        if(report.getRequestAdId() != null)
        {
            if(report.getRequestAdId() < 0)
            {
                //logger.error("BR - RequestAdID can't be negative number");
                return new ResponseEntity<>("Id can't be negative number", HttpStatus.BAD_REQUEST);
            }

        }
        else
        {
            //logger.error("BR - RequestAdID can't be null");
            return new ResponseEntity<>("Id can't be null", HttpStatus.BAD_REQUEST);
        }


        if(this.reportService.createRequestReport(report.getRequestAdId(), report.getContent(), report.getKm())){
            //logger.info("Creating report-For requestAdID: " + report.getRequestAdId() + " created");
            return new ResponseEntity("Report successfully created", HttpStatus.CREATED);
        }
        else
        {
            //logger.info("Creating report-For requestAdID: " + report.getRequestAdId() + " couldn't be created");
            return new ResponseEntity<>("Error creating report", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/reservation",consumes = "application/json")
    public ResponseEntity<?> createReservationReport(@Valid @RequestBody ReportDTO report){

        if(report.getReservationId() != null)
        {
            if(report.getReservationId() < 0)
            {
               // logger.error("BR-ReservationID can't be negative number");
                return new ResponseEntity<>("Id can't be negative number", HttpStatus.BAD_REQUEST);
            }

        }
        else
        {
            //logger.error("BR-ReservationID can't be null");
            return new ResponseEntity<>("Id can't be null", HttpStatus.BAD_REQUEST);
        }


        if(this.reportService.createReservationReport(report.getReservationId(), report.getContent(), report.getKm()))
        {
           // logger.info("Creating report-For reservationID: " + report.getReservationId() + " created");
            return new ResponseEntity("Report successfully created", HttpStatus.CREATED);
        }
        else
        {
           // logger.info("Creating report-For reservationID: " + report.getReservationId() + " couldn't be created");
            return new ResponseEntity<>("Error creating report", HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping(value="/request/{reqAdId}")
    public ResponseEntity<?> showRequestReport(@PathVariable("reqAdId") Long reqAdId){

        ReportDTO  report = this.reportService.showRequestReport(reqAdId);

        if( report != null)
        {
            return new ResponseEntity(report, HttpStatus.CREATED);
        }
        else return new ResponseEntity<>("Report not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping(value="/reservation/{resId}")
    public ResponseEntity<?> showReservationReport(@PathVariable("resId") Long resId){

        ReportDTO  report = this.reportService.showReservationReport(resId);

        if( report != null)
        {
            return new ResponseEntity(report, HttpStatus.CREATED);
        }
        else return new ResponseEntity<>("Report not found", HttpStatus.NOT_FOUND);
    }

}
