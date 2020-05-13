package com.example.team19.repository;

import com.example.team19.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<Advertisement,Long> {
}
