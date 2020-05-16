package com.example.team19.service.impl;

import com.example.team19.dto.PriceListDTO;
import com.example.team19.model.PriceList;
import com.example.team19.repository.PriceListRepository;
import com.example.team19.service.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceListServiceImpl implements PriceListService {
    @Autowired
    private PriceListRepository priceListRepository;

    @Override
    public PriceList findById(Long id) {
        return priceListRepository.findById(id).orElse(null);
    }

    @Override
    public ArrayList<PriceListDTO> getAll() {
        List<PriceList> priceLists = priceListRepository.findAll();
        ArrayList<PriceListDTO> priceListDTOS = new ArrayList<>();
        for (PriceList priceList: priceLists){
            PriceListDTO priceListDTO = new PriceListDTO();
            priceListDTO.setAlias(priceList.getAlias());
            priceListDTO.setDiscount20Days(priceList.getDiscount20Days());
            priceListDTO.setDiscount30Days(priceList.getDiscount30Days());
            priceListDTO.setPricePerDay(priceList.getPricePerDay());
            priceListDTO.setPricePerKm(priceList.getPricePerKm());
            priceListDTO.setId(priceList.getId());

            priceListDTOS.add(priceListDTO);
        }

        return priceListDTOS;
    }

}
