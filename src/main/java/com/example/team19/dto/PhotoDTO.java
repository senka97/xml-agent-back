package com.example.team19.dto;

import com.example.team19.model.Car;

import javax.persistence.*;

public class PhotoDTO {


    private Long id;

    private String path;

    public PhotoDTO(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
