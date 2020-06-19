package com.example.team19.repository;


import com.example.team19.model.PriceList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceListRepository extends JpaRepository<PriceList,Long> {

    PriceList findByIdAndRemoved(Long id, boolean removed);
    List<PriceList> findAllByRemoved(boolean removed);
    PriceList findByAliasAndRemoved(String alias, boolean removed);
}
