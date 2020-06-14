package com.example.team19.service.impl;

import com.example.team19.dto.PriceListDTO;
import com.example.team19.model.Advertisement;
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

    @Autowired
    private AdServiceImpl adService;

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

    @Override
    public PriceList createPriceList(PriceListDTO priceList) {

        PriceList newPL = new PriceList();

        newPL.setAlias(priceList.getAlias());
        newPL.setPricePerDay(priceList.getPricePerDay());
        newPL.setPricePerKm(priceList.getPricePerKm());
        newPL.setDiscount20Days(priceList.getDiscount20Days());
        newPL.setDiscount30Days(priceList.getDiscount30Days());

        return priceListRepository.save(newPL);
    }

    @Override
    public Boolean deletePriceList(Long id) {

        PriceList priceList = findById(id);
        if(priceList != null) // ako postoji ovaj cenovnik
        {
            ArrayList<Advertisement> advertisements = adService.findActiveAds(); // vrati sve aktivne oglase (krajnji datum je veci od danasnjeg)
            Boolean canDelete = true;
            for(Advertisement ad : advertisements)
            {
                if(ad.getPriceList().getId() == id) // prodje kroz sve oglase, ako u bilo kom postoji ovaj cenovnik ne moze se obrisati
                {
                    canDelete = false;
                    break;
                }
            }
            if(!canDelete)
            {
                return false;
            }
            else {
                priceListRepository.delete(priceList);
                return true;
            }
        }
        else return false;
    }

    @Override
    public PriceList save(PriceList priceList) {
        return priceListRepository.save(priceList);
    }

}
