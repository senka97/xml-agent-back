package com.example.team19.service;

import com.example.team19.dto.PriceListDTO;
import com.example.team19.dto.PriceListRequestDTO;
import com.example.team19.model.PriceList;


import java.util.ArrayList;

public interface PriceListService {

    PriceList findById(Long id);
    ArrayList<PriceListDTO> getAll();
    PriceListDTO createPriceList(PriceListRequestDTO priceListRequestDTO);
    String deletePriceList(Long id);
    PriceList save(PriceList priceList);
    PriceList findByAlias(String alias);
}
