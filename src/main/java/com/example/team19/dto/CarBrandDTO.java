package com.example.team19.dto;

import com.example.team19.model.CarBrand;

public class CarBrandDTO {

    private Long id;

    private String name;

    public CarBrandDTO()
    {

    }

    public CarBrandDTO(CarBrand carBrand){
        this.id = carBrand.getId();
        this.name = carBrand.getName();
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
}
