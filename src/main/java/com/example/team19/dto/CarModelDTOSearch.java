package com.example.team19.dto;

import com.example.team19.model.CarModel;

public class CarModelDTOSearch {

    private Long value;
    private String text;

    public CarModelDTOSearch(){

    }

    public CarModelDTOSearch(CarModel carModel){
        this.value = carModel.getId();
        this.text = carModel.getName();
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
