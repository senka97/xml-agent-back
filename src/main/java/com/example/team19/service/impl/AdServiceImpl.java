package com.example.team19.service.impl;

import com.example.team19.dto.*;
import com.example.team19.enums.CarClass;
import com.example.team19.enums.FuelType;
import com.example.team19.enums.TransmissionType;
import com.example.team19.model.*;
import com.example.team19.repository.AdRepository;
import com.example.team19.repository.CarBrandRepository;
import com.example.team19.repository.CarModelRepository;
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
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;


@Service
public class AdServiceImpl implements AdService {

    @Autowired
    private AdRepository adRepository;

    @Autowired
    private CarServiceImpl carService;

    @Autowired
    private PriceListServiceImpl priceListService;

    @Autowired
    private CarBrandRepository carBrandRepository;

    @Autowired
    private CarModelRepository carModelRepository;

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
    public ArrayList<AdDTO2> getAllAds()
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
    public ArrayList<AdDTO2> searchAds(AdSearchDTO adSearchDTO) {

        ArrayList<AdDTO2> adsDTO = new ArrayList<>();
        String location = adSearchDTO.getLocation();
        LocalDate startDate = adSearchDTO.getStartDate();
        LocalDate endDate = adSearchDTO.getEndDate();
        //osnovna pretraga
        List<Advertisement> ads = adRepository.basicSearchAds(location, startDate, endDate);

        for(Advertisement ad : ads) {
            if(adSearchDTO.getCdw() != null){
                boolean cdw = Boolean.parseBoolean(adSearchDTO.getCdw());
                if(ad.getCdw() != cdw) {
                    continue;
                }
            }
            if(adSearchDTO.getPlannedMileage() > 0){ //ako je 0 onda znaci da se ovo ne gleda
                if(ad.getLimitKm() != 0){ //ovo znaci da nije UNLIMITED, ako je 0 onda je UNLIMITED
                    if(adSearchDTO.getPlannedMileage() > ad.getLimitKm()){ //ako je planirana kilometraza veca od limita
                        continue;
                    }
                }
            }
            if(adSearchDTO.getCarBrand() != null){ //ako se trazi po brandu
                if(ad.getCar().getCarModel().getCarBrand().getId() != adSearchDTO.getCarBrand()){
                    continue;
                }
            }

            if(adSearchDTO.getCarModel() != null){ //ako se trazi po modelu
                if(ad.getCar().getCarModel().getId() != adSearchDTO.getCarModel()){
                    continue;
                }
            }

            if(adSearchDTO.getCarClass() != null){
                if(!ad.getCar().getCarClass().toString().equals(adSearchDTO.getCarClass())){
                    continue;
                }
            }

            if(adSearchDTO.getTransmissionType() != null){
                if(!ad.getCar().getTransType().toString().equals(adSearchDTO.getTransmissionType())){
                    continue;
                }
            }

            if(adSearchDTO.getFuelType() != null){
                if(!ad.getCar().getFuelType().toString().equals(adSearchDTO.getFuelType())){
                    continue;
                }
            }

            //ako je razlicito od null i "" trazi se po tome
            if(adSearchDTO.getChildrenSeats() != null && !adSearchDTO.getChildrenSeats().equals("")){
                int childrenSeats = Integer.parseInt(adSearchDTO.getChildrenSeats());
                if(ad.getCar().getChildrenSeats() != childrenSeats){
                    continue;
                }
            }

            if(adSearchDTO.getMileage() != null && !adSearchDTO.getMileage().equals("")){
                double mileage = Double.parseDouble(adSearchDTO.getMileage());
                if(ad.getCar().getMileage() > mileage){
                    continue;
                }
            }

            if(adSearchDTO.getMinPrice() > 0 ){
                if(ad.getPriceList().getPricePerDay()<adSearchDTO.getMinPrice()){
                    continue;
                }
            }
            if(adSearchDTO.getMaxPrice() > 0){
                if(ad.getPriceList().getPricePerDay() > adSearchDTO.getMaxPrice()){
                    continue;
                }
            }
           adsDTO.add(makeAdDTO(ad));
        }

        return adsDTO;
    }

    @Override
    public String validateAdDTO(AdSearchDTO adSearchDTO) {
        String msg = "";
        boolean valid = true;
        if(adSearchDTO.getLocation() == null) {
            msg += "Location is mandatory. ";
            valid = false;
        }else{
            if(!Pattern.matches("[a-zA-Z ]+$", adSearchDTO.getLocation())){
                msg += "Location doesn't have valid format.";
                valid = false;
            }else{
                if(adSearchDTO.getLocation().trim().length()==0){
                    msg += "Location can't have only white spaces.";
                    valid = false;
                }else {
                    if (adSearchDTO.getLocation().length() > 100) {
                        msg += "Location name is too long.";
                        valid = false;
                    }
                }
            }
        }
        if(adSearchDTO.getStartDate() == null || adSearchDTO.getEndDate() == null) {
            msg += "Start date and end date are mandatory. ";
            valid = false;
        }else{
            if(adSearchDTO.getStartDate().isBefore(LocalDate.now().plusDays(2))){
                msg += "Start date has to be 48 hours after this moment.";
                valid = false;
            }else{
                if(!adSearchDTO.getStartDate().isBefore(adSearchDTO.getEndDate())){
                    msg += "End date has to be after start date.";
                    valid = false;
                }
            }
        }
        if(!valid){
            return msg;
        }
        //provera za cdw, ide String jer ako je null onda se ovo ne proverava
        if(adSearchDTO.getCdw() != null){
            if(!adSearchDTO.getCdw().equals("true") && !adSearchDTO.getCdw().equals("false")){
                msg += "Cdw doesn't have valid format. It should be true or false.";
                return msg;
            }
        }
        //provera za carBrand
        if(adSearchDTO.getCarBrand() != null){ //ako je 0 onda se ne proverava
            if(adSearchDTO.getCarBrand()<=0){
                msg += "Car brand id is not valid.";
                return msg;
            }
            CarBrand cb = carBrandRepository.findById(adSearchDTO.getCarBrand()).orElse(null);
            if(cb == null){
                msg += "This car brand doesn't exist.";
                return msg;
            }
        }
        //provera za carModel
        if(adSearchDTO.getCarModel() != null){
            if(adSearchDTO.getCarModel() <= 0){
                msg += "Car model id is not valid.";
                return msg;
            }
            CarModel cm = carModelRepository.findById(adSearchDTO.getCarModel()).orElse(null);
            if(cm == null){
                msg += "This car model doesn't exist.";
                return msg;
            }else{
                if(adSearchDTO.getCarBrand() > 0) {
                    if (cm.getCarBrand().getId() != adSearchDTO.getCarBrand()) {
                        //to nije model od izabranog brenda
                        msg += "Car brand doesn't have this model.";
                        return msg;
                    }
                }
            }
        }

        if(adSearchDTO.getCarClass() != null){
            if(!isCarClass(adSearchDTO.getCarClass())){
                msg += "This car class doesn't exist.";
                return msg;
            }
        }
        if(adSearchDTO.getFuelType() != null){
            if(!isFuelType(adSearchDTO.getFuelType())){
                msg += "This fuel type doesn't exist.";
                return msg;
            }
        }
        if(adSearchDTO.getTransmissionType() != null){
            if(!isTransType(adSearchDTO.getTransmissionType())){
                msg += "This transmission type doesn't exist.";
                return msg;
            }
        }
        //ako je razlicito od null i "" proverava se
        if(adSearchDTO.getChildrenSeats() != null && !adSearchDTO.getChildrenSeats().equals("")){
            if(!Pattern.matches("[0|1|2|3|4]$", adSearchDTO.getChildrenSeats())){
                msg += "Children seats has to be a number from 0 to 4.";
                return msg;
            }
        }
        //moze biti nula ako se trazi auto sa 0 predjenih kilometara, odnosno nov
        if(adSearchDTO.getMileage() != null && !adSearchDTO.getMileage().equals("")){
            try {
                double mileage = Double.parseDouble(adSearchDTO.getMileage());
                if(mileage < 0){
                    msg += "Mileage has to be positive number.";
                    return msg;
                }
            }catch (NumberFormatException e){
                msg += "Mileage doesn't have valid format.";
                return msg;
            }
        }

        if(adSearchDTO.getPlannedMileage() < 0){
            msg += "Planned mileage has to be positive number.";
            return msg;
        }

        if(adSearchDTO.getMinPrice()<0){
            msg += "Minimum price has to be positive number.";
            return msg;
        }
        if(adSearchDTO.getMaxPrice()<0){
            msg += "Maximum price has to be positive number.";
            return msg;
        }

        if(adSearchDTO.getMinPrice()>0 && adSearchDTO.getMaxPrice()>0){
            if(adSearchDTO.getMinPrice() > adSearchDTO.getMaxPrice()){
                msg += "Maximum price has to be greater than minim price.";
                return msg;
            }
        }
        return null;
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

            if(car.getMainId() == null)
                car.setMainId(adDTO.getCar().getMainId());

            carService.save(car);
        }

        newAd.setMainId(adDTO.getId());
        newAd.setCar(car);
        newAd.setCdw(adDTO.isCdw());
        newAd.setEndDate(adDTO.getEndDate());
        newAd.setStartDate(adDTO.getStartDate());
        newAd.setLimitKm(adDTO.getLimitKm());
        newAd.setLocation(adDTO.getLocation());


        PriceList priceList = priceListService.findById(adDTO.getPriceList().getId());
        priceList.setMainId(adDTO.getPriceList().getMainId());
        priceList = priceListService.save(priceList);
        newAd.setPriceList(priceList);

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

    public static boolean isCarClass(String value) {
        return Arrays.stream(CarClass.values()).anyMatch(e -> e.name().equals(value));
    }
    public static boolean isTransType(String value) {
        return Arrays.stream(TransmissionType.values()).anyMatch(e -> e.name().equals(value));
    }
    public static boolean isFuelType(String value) {
        return Arrays.stream(FuelType.values()).anyMatch(e -> e.name().equals(value));
    }
}
