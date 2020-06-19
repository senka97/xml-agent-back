package com.example.team19.repository;

import com.example.team19.model.RequestAd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestAdRepository extends JpaRepository<RequestAd, Long> {
}
