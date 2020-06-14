package com.example.team19.service.impl;

import com.example.team19.dto.CarDTO;
import com.example.team19.dto.CarStatisticDTO;
import com.example.team19.model.Advertisement;
import com.example.team19.model.Car;
import com.example.team19.model.Photo;
import com.example.team19.repository.CarRepository;
import com.example.team19.service.CarModelService;
import com.example.team19.service.CarService;
import com.example.team19.service.PhotoService;
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
        List<Car> cars = carRepository.findAll();
        Car mostKilometers = cars.get(0);
        for(Car car: cars){
            if(mostKilometers.getMileage() < car.getMileage())
                mostKilometers=car;
        }
        return new CarStatisticDTO(mostKilometers);
    }

    @Override
    public CarStatisticDTO getCarWithBestScore() {
        List<Car> cars = carRepository.findAll();
        Car bestScore = cars.get(0);
        for(Car car: cars){
            if(bestScore.getRate() < car.getRate())
                bestScore=car;
        }
        return new CarStatisticDTO(bestScore);
    }

    @Override
    public CarStatisticDTO getCarWithMostComments() {
        List<Car> cars = carRepository.findAll();
        Car mostComments = cars.get(0);
        for(Car car: cars){
            if(car.getComments() != null) {

                if (mostComments == null)
                    mostComments = car;

                if (mostComments.getComments().size() < car.getComments().size())
                    mostComments = car;
            }
        }
        return new CarStatisticDTO(mostComments);
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
