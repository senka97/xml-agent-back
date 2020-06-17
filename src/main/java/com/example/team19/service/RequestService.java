package com.example.team19.service;

import com.example.team19.dto.RequestFrontDTO;
import com.example.team19.model.Request;
import com.example.team19.wsdl.AcceptPendingRResponse;

import java.time.LocalDate;
import java.util.List;

public interface RequestService {

    Request findById(Long id);
    List<RequestFrontDTO> getPendingRequestsFront();
    List<RequestFrontDTO> getPaidRequestsFront();
    List<Request> findPaidRequestsForAdForThisPeriod(Long id, LocalDate startDate, LocalDate endDate);
    String rejectPendingRequest(Long id);
    String acceptPendingRequest(Long id);
    Request findByMainId(Long id);
    Request save(Request request);
    int getPendingRequestsNumber();

}
