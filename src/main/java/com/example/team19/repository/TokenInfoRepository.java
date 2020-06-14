package com.example.team19.repository;

import com.example.team19.model.CarModel;
import com.example.team19.model.TokenInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenInfoRepository extends JpaRepository<TokenInfo,Long> {
}
