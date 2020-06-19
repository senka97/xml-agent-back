package com.example.team19.service.impl;

import com.example.team19.dto.PriceListDTO;
import com.example.team19.dto.PriceListRequestDTO;
import com.example.team19.model.Advertisement;
import com.example.team19.model.PriceList;
import com.example.team19.repository.PriceListRepository;
import com.example.team19.service.PriceListService;
import com.example.team19.soap.AdClient;
import com.example.team19.wsdl.AddPriceListRequest;
import com.example.team19.wsdl.AddPriceListResponse;
import com.example.team19.wsdl.DeletePriceListRequest;
import com.example.team19.wsdl.DeletePriceListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PriceListServiceImpl implements PriceListService {
    @Autowired
    private PriceListRepository priceListRepository;

    @Autowired
    private AdServiceImpl adService;

    @Autowired
    private AdClient adClient;

    @Override
    public PriceList findById(Long id) {
        return priceListRepository.findById(id).orElse(null);
    }

    @Override
    public ArrayList<PriceListDTO> getAll() {
        List<PriceList> priceLists = priceListRepository.findAllByRemoved(false);
        ArrayList<PriceListDTO> priceListDTOS = new ArrayList<>();
        for (PriceList priceList: priceLists){
            PriceListDTO priceListDTO = new PriceListDTO();
            priceListDTO.setAlias(priceList.getAlias());
            priceListDTO.setDiscount20Days(priceList.getDiscount20Days());
            priceListDTO.setDiscount30Days(priceList.getDiscount30Days());
            priceListDTO.setPricePerDay(priceList.getPricePerDay());
            priceListDTO.setPricePerKm(priceList.getPricePerKm());
            priceListDTO.setPriceForCdw(priceList.getPriceForCdw());
            priceListDTO.setId(priceList.getId());

            priceListDTOS.add(priceListDTO);
        }

        return priceListDTOS;
    }

    @Override
    public PriceListDTO createPriceList(PriceListRequestDTO priceListRequestDTO) {

        /*PriceList newPL = new PriceList();
        newPL.setAlias(priceList.getAlias());
        newPL.setPricePerDay(priceList.getPricePerDay());
        newPL.setPricePerKm(priceList.getPricePerKm());
        newPL.setDiscount20Days(priceList.getDiscount20Days());
        newPL.setDiscount30Days(priceList.getDiscount30Days());
        return priceListRepository.save(newPL);*/

        AddPriceListResponse apResponse = this.adClient.addPriceList(priceListRequestDTO);
        if(apResponse.isSuccess()){
            PriceList priceList = new PriceList(priceListRequestDTO);
            priceList.setMainId(apResponse.getMainId());
            priceList = this.priceListRepository.save(priceList);
            return new PriceListDTO(priceList);
        }else{
            PriceListDTO priceListDTO = new PriceListDTO();
            priceListDTO.setMainId(0l); //ovo oznacava da je greska bila na glavnoj aplikaciji
            priceListDTO.setAlias(apResponse.getMessage());
            return  priceListDTO;
        }

    }

    @Override
    public String deletePriceList(Long id) {

        /*PriceList priceList = findById(id);
        if(priceList != null) { // ako postoji ovaj cenovnik
            ArrayList<Advertisement> advertisements = adService.findActiveAds(); // vrati sve aktivne oglase (krajnji datum je veci od danasnjeg)
            Boolean canDelete = true;
            for(Advertisement ad : advertisements) {
                if(ad.getPriceList().getId() == id){ // prodje kroz sve oglase, ako u bilo kom postoji ovaj cenovnik ne moze se obrisati
                    canDelete = false;
                    break;
                }
            }
            if(!canDelete) {
                return false;
            }
            else {
                priceListRepository.delete(priceList);
                return true;
            }
        } else return false;*/

        PriceList priceList = this.findById(id);
        List<Advertisement> activeAds = this.adService.findActiveAdsForThisPriceList(id, LocalDate.now());
        if(activeAds.size()>0){
            return "This price list can't be deleted because there are active ads which use it.";
        }

        DeletePriceListResponse dpResponse = this.adClient.deletePriceList(priceList.getMainId());
        if(dpResponse.isSuccess()) {
            priceList.setRemoved(true);
            this.priceListRepository.save(priceList);
            return null;
        }else{
            return dpResponse.getMessage();
        }
    }

    @Override
    public PriceList save(PriceList priceList) {
        return priceListRepository.save(priceList);
    }

    @Override
    public PriceList findByAlias(String alias) {
        return this.priceListRepository.findByAliasAndRemoved(alias,false);
    }

}
