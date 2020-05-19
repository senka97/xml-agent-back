package com.example.team19.repository;

import com.example.team19.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "SELECT * FROM reservation r WHERE r.advertisement_id = ?1 "  , nativeQuery = true)
    Set<Reservation> findReservationsForThisAd(Long id);
}
