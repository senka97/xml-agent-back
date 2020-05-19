package com.example.team19.service;

import com.example.team19.dto.ReservationDTO;
import com.example.team19.model.Reservation;

import java.util.Set;

public interface ReservationService {

    Reservation createNewReservation(ReservationDTO r);
    Set<Reservation> findReservationsForThisAd(Long id);
}
