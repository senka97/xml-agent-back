package com.example.team19.dto;

import com.example.team19.model.CarBrand;

public class CarBrandDTOSearch {

    private Long value;
    private String text;

    public CarBrandDTOSearch(){

    }

    public CarBrandDTOSearch(CarBrand carBrand){
        this.value = carBrand.getId();
        this.text = carBrand.getName();
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
