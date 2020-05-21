package com.example.team19.repository;

import com.example.team19.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.ArrayList;

public interface AdRepository extends JpaRepository<Advertisement,Long> {

    @Query(value = "SELECT * FROM advertisement a WHERE a.end_date >= ?1 "  , nativeQuery = true)
    ArrayList<Advertisement> findActiveAds(LocalDate now);
}
