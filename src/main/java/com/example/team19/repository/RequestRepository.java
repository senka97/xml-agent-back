package com.example.team19.repository;

import com.example.team19.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request,Long> {

       @Query(value="SELECT r.mainId from Request r WHERE r.status='Pending'")
       List<Long> pendingRequestsMainId();

       Request findByMainId(Long id);

       @Query(value="FROM Request r inner join r.requestAds ra WHERE r.status='Paid' AND ra.advertisement.id=?1 AND ((ra.startDate<?2 AND ra.endDate>=?2) OR (ra.startDate>=?2 AND ra.endDate<=?3) OR (ra.startDate<=?3 AND ra.endDate>?3) OR (ra.startDate<?2 AND ra.endDate>?3))")
       List<Request> findPaidRequestsForAdForThisPeriod(Long id, LocalDate startDate, LocalDate endDate);

       @Query(value="FROM Request r WHERE r.status='Paid'")
       List<Request> findPaidRequestsFront();

       @Query(value="FROM Request r WHERE r.status='Pending'")
       List<Request> findPendingRequestsFront();

}
