package com.example.team19.service.impl;

import com.example.team19.dto.*;
import com.example.team19.model.*;
import com.example.team19.repository.AdRepository;
import com.example.team19.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;


@Service
public class AdServiceImpl implements AdService {

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private CarServiceImpl carService;

    @Autowired
    private PriceListServiceImpl priceListService;

    @Override
    public Advertisement findById(Long id) {
      return adRepository.findById(id).orElse(null);
    }

    @Override
    public ArrayList<Advertisement> findActiveAds() {

        LocalDate today = LocalDate.now();
        ArrayList<Advertisement> advertisements = adRepository.findActiveAds(today);

        return advertisements;
    }

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
        newAd.setLocation(adDTO.getLocation());
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
        ArrayList<String> photos64 = new ArrayList<>();

        for(Photo p : photos)
        {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            Path path = Paths.get(p.getPath());
            // write the image to a file
            System.out.println(p.getPath());
            File input = path.toFile();
            BufferedImage img = null;
            try {
                img = ImageIO.read(input);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(img != null) {
                try {

                    ImageIO.write(img, "png", bos);
                    byte[] imageBytes = bos.toByteArray();


                    String imageString = Base64.getEncoder().encodeToString(imageBytes);
                    String retStr = "data:image/png;base64," + imageString;
                    photos64.add(retStr);
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        newCar.setPhotos64(photos64);

        newAd.setId(ad.getId());
        newAd.setStartDate(ad.getStartDate());
        newAd.setEndDate(ad.getEndDate());
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
