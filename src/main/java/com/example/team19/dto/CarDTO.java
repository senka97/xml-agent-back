package com.example.team19.dto;

import com.example.team19.enums.CarClass;
import com.example.team19.enums.FuelType;
import com.example.team19.enums.TransmissionType;
import com.example.team19.model.*;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Set;

public class CarDTO {

    private Long id;

    private int childrenSeats;

    private double rate;

    private double mileage;

    private boolean hasAndroidApp;

    private CarBrandDTO carBrand;

    private CarModelDTO carModel;

    private CarClass carClass;

    private TransmissionType transType;

    private FuelType fuelType;

    private Set<PhotoDTO> photos;

    private ArrayList<String> photos64 = new ArrayList<>();

    private int numberOfComments;

    private Long mainId;

    public CarDTO(){

    }

    public CarDTO(Car car){
        id = car.getId();
        childrenSeats = car.getChildrenSeats();
        rate = car.getRate();
        mileage = car.getMileage();
        carBrand = new CarBrandDTO();
        carModel = new CarModelDTO(car.getCarModel());
        carClass = car.getCarClass();
        transType = car.getTransType();
        fuelType = car.getFuelType();
        hasAndroidApp = car.getHasAndroidApp();
        numberOfComments = car.getComments().size();
        mainId = car.getMainId();
        //slike
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
                        photos64.add(retStr);
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getChildrenSeats() {
        return childrenSeats;
    }

    public void setChildrenSeats(int childrenSeats) {
        this.childrenSeats = childrenSeats;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public CarModelDTO getCarModel() {
        return carModel;
    }

    public void setCarModel(CarModelDTO carModel) {
        this.carModel = carModel;
    }

    public CarClass getCarClass() {
        return carClass;
    }

    public void setCarClass(CarClass carClass) {
        this.carClass = carClass;
    }

    public TransmissionType getTransType() {
        return transType;
    }

    public void setTransType(TransmissionType transType) {
        this.transType = transType;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public Set<PhotoDTO> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<PhotoDTO> photos) {
        this.photos = photos;
    }

    public CarBrandDTO getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(CarBrandDTO carBrand) {
        this.carBrand = carBrand;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public boolean getHasAndroidApp() {
        return hasAndroidApp;
    }

    public void setHasAndroidApp(boolean hasAndroidApp) {
        this.hasAndroidApp = hasAndroidApp;
    }

    public boolean isHasAndroidApp() {
        return hasAndroidApp;
    }

    public ArrayList<String> getPhotos64() {
        return photos64;
    }

    public void setPhotos64(ArrayList<String> photos64) {
        this.photos64 = photos64;
    }

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    public Long getMainId() {
        return mainId;
    }

    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }
}
