package com.example.team19.dto;


import com.example.team19.model.CarModel;

public class CarModelDTO {

    private Long id;

    private String name;

    private  CarBrandDTO carBrand;

    public CarModelDTO()
    {

    }

    public CarModelDTO(CarModel carModel){
        id = carModel.getId();
        name = carModel.getName();
        carBrand = new CarBrandDTO();
        carBrand.setId(carModel.getCarBrand().getId());
        carBrand.setName(carModel.getCarBrand().getName());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CarBrandDTO getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(CarBrandDTO carBrand) {
        this.carBrand = carBrand;
    }
}
