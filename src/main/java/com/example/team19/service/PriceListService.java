package com.example.team19.service;

import com.example.team19.dto.PriceListDTO;
import com.example.team19.model.PriceList;


import java.util.ArrayList;

public interface PriceListService {

    PriceList findById(Long id);
    ArrayList<PriceListDTO> getAll();
}
