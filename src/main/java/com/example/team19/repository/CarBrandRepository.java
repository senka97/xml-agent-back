package com.example.team19.repository;

import com.example.team19.model.Advertisement;
import com.example.team19.model.CarBrand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarBrandRepository extends JpaRepository<CarBrand,Long> {

    CarBrand findByName(String name);
}
