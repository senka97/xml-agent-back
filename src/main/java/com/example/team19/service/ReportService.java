package com.example.team19.service;

import com.example.team19.dto.ReportDTO;

public interface ReportService {

    Boolean createRequestReport(Long requestAdId, String content, double km);
    Boolean createReservationReport(Long reservationId, String content, double km);
    ReportDTO showRequestReport(Long id);
    ReportDTO showReservationReport(Long id);
}
