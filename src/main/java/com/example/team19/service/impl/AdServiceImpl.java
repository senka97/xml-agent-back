package com.example.team19.service.impl;

import com.example.team19.dto.*;
import com.example.team19.model.*;
import com.example.team19.repository.AdRepository;
import com.example.team19.service.AdService;
import com.example.team19.service.PriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
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

    @Autowired
    private PriceListServiceImpl priceListService;

    @Override
    public ArrayList<AdDTO2> searchAds()
    {
        ArrayList<AdDTO2> DTOAds = new ArrayList<>();
        List<Advertisement> ads = adRepository.findAll();

        for(Advertisement ad : ads)
        {
            DTOAds.add(makeAdDTO(ad));
        }

        return DTOAds;
    }

    @Override
    public Advertisement save(Advertisement ad) {
        return adRepository.save(ad);
    }

    @Override
    public Advertisement createNewAd(AdDTO adDTO) {

        Advertisement newAd = new Advertisement();

        Car car = new Car();

        if(adDTO.getCar().getId() == null) {
            //novi auto
            car = carService.createNewCar(adDTO.getCar());
        }else{
            //auto vec postoji
            car = carService.findById(adDTO.getCar().getId());
        }


        newAd.setCar(car);
        newAd.setCdw(adDTO.isCdw());
        newAd.setEndDate(adDTO.getEndDate());
        newAd.setStartDate(adDTO.getStartDate());
        newAd.setLimitKm(adDTO.getLimitKm());
       // newAd.setLocation(adDTO.getLocation());
        newAd.setLocation("Novi Sad"); // izbrisi ovo kada namestis u formi da se unosi lokacija
        newAd.setPriceList(priceListService.findById(adDTO.getPriceList().getId()));

        Advertisement createdAd = save(newAd);


        return createdAd;
    }

    @Override
    public AdDTO2 getAd(Long id) {

        Advertisement ad = adRepository.findById(id).orElse(null);
        if(ad != null)
        {
            return makeAdDTO(ad);
        }
        else return null;
    }

    public AdDTO2 makeAdDTO(Advertisement ad)
    {
        AdDTO2 newAd = new AdDTO2();
        Car car = ad.getCar();
        CarDTO2 newCar = new CarDTO2();
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MMM yyyy.");
        newAd.setStartDate(ad.getStartDate().format(formatter));
        newAd.setEndDate(ad.getEndDate().format(formatter));
        newAd.setLimitKm(ad.getLimitKm());
        newAd.setCdw(ad.getCdw());
        newAd.setLocation(ad.getLocation());

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
        newCar.setHasAndroidApp(car.getHasAndroidApp());

        carBrandDTO.setId(carBrand.getId());
        carBrandDTO.setName(carBrand.getName());
        carModelDTO.setId(carModel.getId());
        carModelDTO.setName(carModel.getName());

        newCar.setCarModel(carModelDTO);
        newCar.setCarBrand(carBrandDTO);

        newCar.setCarClass(car.getCarClass().toString().replace('_',' '));
        newCar.setTransType(car.getTransType().toString().replace('_',' '));
        newCar.setFuelType(car.getFuelType().toString().replace('_',' '));

        newAd.setCar(newCar);
        return newAd;
    }
}
