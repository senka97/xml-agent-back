package com.example.team19.repository;

import com.example.team19.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report,Long> {

    @Query(value="FROM Report r WHERE r.requestAd.id=?1")
    Report findByRequestAdId(Long id);

    @Query(value="FROM Report r WHERE r.reservation.id=?1")
    Report findByReservationId(Long id);
}
