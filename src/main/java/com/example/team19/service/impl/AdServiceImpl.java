package com.example.team19.service.impl;

import com.example.team19.dto.*;
import com.example.team19.model.*;
import com.example.team19.repository.AdRepository;
import com.example.team19.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class AdServiceImpl implements AdService {

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private CarServiceImpl carService;

    @Override
    public ArrayList<AdDTO> searchAds()
    {
        ArrayList<AdDTO> DTOAds = new ArrayList<>();
        List<Advertisement> ads = adRepository.findAll();

        for(Advertisement ad : ads)
        {
            AdDTO newAd = new AdDTO();
            Car car = ad.getCar();
            CarDTO newCar = new CarDTO();
            CarModel carModel = car.getCarModel();
            CarModelDTO carModelDTO = new CarModelDTO();
            CarBrand carBrand = carModel.getCarBrand();
            CarBrandDTO carBrandDTO = new CarBrandDTO();
            PriceList priceList = ad.getPriceList();
            PriceListDTO priceListDTO = new PriceListDTO();
            Set<Photo> photos = car.getPhotos();
            Set<PhotoDTO> photoDTO = new HashSet<>();

            for(Photo p : photos)
            {
                PhotoDTO newPhoto = new PhotoDTO();
                newPhoto.setId(p.getId());
                newPhoto.setPath(p.getPath());

                photoDTO.add(newPhoto);
            }

            newCar.setPhotos(photoDTO);

            newAd.setId(ad.getId());
            newAd.setStartDate(ad.getStartDate());
            newAd.setEndDate(ad.getEndDate());
            newAd.setLimitKm(ad.getLimitKm());
            newAd.setCdw(ad.getCdw());

            priceListDTO.setId(priceList.getId());
            priceListDTO.setAlias(priceList.getAlias());
            priceListDTO.setPricePerDay(priceList.getPricePerDay());
            priceListDTO.setPricePerKm(priceList.getPricePerKm());
            priceListDTO.setDiscount20Days(priceList.getDiscount20Days());
            priceListDTO.setDiscount30Days(priceList.getDiscount30Days());

            newAd.setPriceList(priceListDTO);

            newCar.setId(car.getId());
            newCar.setChildrenSeats(car.getChildrenSeats());
            newCar.setRate(car.getRate());
            newCar.setMileage(car.getMileage());

            carBrandDTO.setId(carBrand.getId());
            carBrandDTO.setName(carBrand.getName());
            carModelDTO.setId(carModel.getId());
            carModelDTO.setName(carModel.getName());

            newCar.setCarModel(carModelDTO);
            newCar.setCarBrand(carBrandDTO);

            newCar.setCarClass(car.getCarClass());
            newCar.setTransType(car.getTransType());
            newCar.setFuelType(car.getFuelType());

            newAd.setCar(newCar);
            DTOAds.add(newAd);
        }

        return DTOAds;
    }
}
