package com.example.team19.service;

import com.example.team19.dto.ReservationDTO;
import com.example.team19.dto.ReservationFrontDTO;
import com.example.team19.dto.ReservationResponseDTO;
import com.example.team19.model.Reservation;

import java.util.List;
import java.util.Set;

public interface ReservationService {

    ReservationResponseDTO createNewReservation(ReservationDTO r);
    Set<Reservation> findReservationsForThisAd(Long id);
    List<ReservationFrontDTO> getReservationsFront();
}
