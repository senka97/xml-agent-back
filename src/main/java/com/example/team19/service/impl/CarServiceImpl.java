package com.example.team19.service.impl;

import com.example.team19.dto.CarBrandDTO;
import com.example.team19.dto.CarDTO;
import com.example.team19.dto.CarModelDTO;
import com.example.team19.dto.CarStatisticDTO;
import com.example.team19.model.*;
import com.example.team19.repository.CarRepository;
import com.example.team19.service.CarModelService;
import com.example.team19.service.CarService;
import com.example.team19.service.PhotoService;
import com.example.team19.soap.CarClient;
import com.example.team19.wsdl.GetCarWithBestRateResponse;
import com.example.team19.wsdl.GetCarWithMostCommentsResponse;
import com.example.team19.wsdl.GetCarWithMostKilometersResponse;
import com.mysql.cj.util.Base64Decoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private CarModelService carModelService;

    @Autowired
    private PhotoServiceImpl photoService;

    @Autowired
    private CarClient carClient;

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car findById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

    @Override
    public ArrayList<CarDTO> getAllAvailableCars() {
        ArrayList<CarDTO> carsDTO = new ArrayList<>();
        List<Car> cars = carRepository.findAll();
        LocalDate now =LocalDate.now();

        for(Car car: cars){
            boolean hasActiveAd = false;
            if(car.getAdvertisements() != null)
                for(Advertisement ad : car.getAdvertisements()){
                    if(ad.getEndDate() != null) {
                        if (ad.getEndDate().isAfter(now) || ad.getEndDate().equals(now)) { //TODO videti jos sta znaci da je oglas aktivan
                            hasActiveAd = true;
                            break;
                        }
                    }
                }

            if(!hasActiveAd){
                CarDTO carDTO = new CarDTO(car);
                carsDTO.add(carDTO);
            }
        }


        return carsDTO;
    }

    @Override
    public Car createNewCar(CarDTO carDTO) {

        Car car = new Car();
        car.setMainId(carDTO.getMainId());
        car.setCarModel(carModelService.findById(carDTO.getCarModel().getId()));
        car.setCarClass(carDTO.getCarClass());
        car.setChildrenSeats(carDTO.getChildrenSeats());
        car.setFuelType(carDTO.getFuelType());
        car.setTransType(carDTO.getTransType());
        car.setHasAndroidApp(carDTO.getHasAndroidApp());
        car.setMileage(carDTO.getMileage());
        car.setAndroidToken(carDTO.getAndroidToken());
        //Dodavanje Slika
        //Prima slike kao base64 string prebacuje ih u slike i snima ih u folder carPictures
        //taj folder morate napraviti kod sebe na C da bi radilo
        //slika se snima po templejtu /carPictures/idCar_brSlike.png
        Car newCar = save(car);

        int i= 0;
        if(carDTO.getPhotos64() !=null) {
            for (String photo : carDTO.getPhotos64()) {


                // create a buffered image
                String[] parts = photo.split(",");
                String imageString = parts[1];


                BufferedImage image = null;
                byte[] imageByte;


                Base64Decoder decoder = new Base64Decoder();
                imageByte = Base64.getDecoder().decode(imageString);
                ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
                try {
                    image = ImageIO.read(bis);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String folder = "./carPictures/";

                Path directoryPath = Paths.get(folder);
                File directory = directoryPath.toFile();
                if (!directory.exists()) {
                    directory.mkdir();
                }

                Path path = Paths.get(folder + car.getId() + "_" + i + ".png");
                // write the image to a file
                File outputfile = path.toFile();
                try {
                    ImageIO.write(image, "png", outputfile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Photo p = new Photo();
                p.setCar(car);
                p.setPath(folder + car.getId() + "_" + i + ".png");


                photoService.save(p);

                i++;
            }
        }



        return newCar;
    }

    @Override
    public CarStatisticDTO getCarWithMostKilometers() {
        GetCarWithMostKilometersResponse mostKilometersResponse = carClient.getCarWithMostKilometers();
        CarStatisticDTO mostKillometers= new CarStatisticDTO();

        CarModelDTO carModel = new CarModelDTO();
        carModel.setName(mostKilometersResponse.getCarSOAP().getCarModel());
        CarBrandDTO carBrand = new CarBrandDTO();
        carBrand.setName(mostKilometersResponse.getCarSOAP().getCarBrand());
        carModel.setCarBrand(carBrand);

        mostKillometers.setCarModel(carModel);
        mostKillometers.setCarClass(mostKilometersResponse.getCarSOAP().getCarClass());
        mostKillometers.setChildrenSeats(mostKilometersResponse.getCarSOAP().getChildrenSeats());
        mostKillometers.setFuelType(mostKilometersResponse.getCarSOAP().getFeulType());
        mostKillometers.setHasAndroidApp(mostKilometersResponse.getCarSOAP().isHasAndroidApp());
        mostKillometers.setMileage(mostKilometersResponse.getCarSOAP().getMileage());
        mostKillometers.setNumberOfComments(mostKilometersResponse.getCarSOAP().getNumberOfComments());
        mostKillometers.setRate(mostKilometersResponse.getCarSOAP().getRate());
        mostKillometers.setTransType(mostKilometersResponse.getCarSOAP().getTransType());

        //photos
        for(String img :mostKilometersResponse.getCarSOAP().getPhotos64()){
            mostKillometers.getPhotos64().add(img);
        }
        return mostKillometers;
    }

    @Override
    public CarStatisticDTO getCarWithBestScore() {
        GetCarWithBestRateResponse bestRateResponse = carClient.getCarWithBestRate();
        CarStatisticDTO bestRate= new CarStatisticDTO();

        CarModelDTO carModel = new CarModelDTO();
        carModel.setName(bestRateResponse.getCarSOAP().getCarModel());
        CarBrandDTO carBrand = new CarBrandDTO();
        carBrand.setName(bestRateResponse.getCarSOAP().getCarBrand());
        carModel.setCarBrand(carBrand);

        bestRate.setCarModel(carModel);
        bestRate.setCarClass(bestRateResponse.getCarSOAP().getCarClass());
        bestRate.setChildrenSeats(bestRateResponse.getCarSOAP().getChildrenSeats());
        bestRate.setFuelType(bestRateResponse.getCarSOAP().getFeulType());
        bestRate.setHasAndroidApp(bestRateResponse.getCarSOAP().isHasAndroidApp());
        bestRate.setMileage(bestRateResponse.getCarSOAP().getMileage());
        bestRate.setNumberOfComments(bestRateResponse.getCarSOAP().getNumberOfComments());
        bestRate.setRate(bestRateResponse.getCarSOAP().getRate());
        bestRate.setTransType(bestRateResponse.getCarSOAP().getTransType());

        //photos
        for(String img :bestRateResponse.getCarSOAP().getPhotos64()){
            bestRate.getPhotos64().add(img);
        }
        return bestRate;
    }

    @Override
    public CarStatisticDTO getCarWithMostComments() {

        GetCarWithMostCommentsResponse mostCommentsResponse = carClient.getCarWithMostComments();
        CarStatisticDTO mostComments= new CarStatisticDTO();

        CarModelDTO carModel = new CarModelDTO();
        carModel.setName(mostCommentsResponse.getCarSOAP().getCarModel());
        CarBrandDTO carBrand = new CarBrandDTO();
        carBrand.setName(mostCommentsResponse.getCarSOAP().getCarBrand());
        carModel.setCarBrand(carBrand);

        mostComments.setCarModel(carModel);
        mostComments.setCarClass(mostCommentsResponse.getCarSOAP().getCarClass());
        mostComments.setChildrenSeats(mostCommentsResponse.getCarSOAP().getChildrenSeats());
        mostComments.setFuelType(mostCommentsResponse.getCarSOAP().getFeulType());
        mostComments.setHasAndroidApp(mostCommentsResponse.getCarSOAP().isHasAndroidApp());
        mostComments.setMileage(mostCommentsResponse.getCarSOAP().getMileage());
        mostComments.setNumberOfComments(mostCommentsResponse.getCarSOAP().getNumberOfComments());
        mostComments.setRate(mostCommentsResponse.getCarSOAP().getRate());
        mostComments.setTransType(mostCommentsResponse.getCarSOAP().getTransType());

        //photos
        for(String img :mostCommentsResponse.getCarSOAP().getPhotos64()){
            mostComments.getPhotos64().add(img);
        }
        return mostComments;
    }

    @Override
    public ArrayList<String> getCarPhotos(Long id) {
        ArrayList<String> ret = new ArrayList<>();
        Car car = carRepository.getOne(id);

        if(car.getPhotos() != null) {
            for (Photo p : car.getPhotos()) {
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
                        ret.add(retStr);
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return ret;
    }


}
