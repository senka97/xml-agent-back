package com.example.team19.repository;

import com.example.team19.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "SELECT * FROM reservation r WHERE r.advertisement_id = ?1 "  , nativeQuery = true)
    Set<Reservation> findReservationsForThisAd(Long id);

    @Query(value="FROM Reservation r WHERE r.advertisement.id=?1 AND ((r.startDate<?2 AND r.endDate>=?2) OR (r.startDate>=?2 AND r.endDate<=?3) OR (r.startDate<=?3 AND r.endDate>?3) OR (r.startDate<?2 AND r.endDate>?3))")
    List<Reservation> findReservationsForThisAdForThisPeriod(Long id, LocalDate startDate, LocalDate endDate);
}
