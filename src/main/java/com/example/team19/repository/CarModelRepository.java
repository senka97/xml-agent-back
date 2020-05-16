package com.example.team19.repository;

import com.example.team19.model.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarModelRepository extends JpaRepository<CarModel,Long> {

    CarModel findByName(String name);
}
