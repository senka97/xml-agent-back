package com.example.team19.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class CarBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="name", nullable=false)
    private String name;
    @OneToMany(mappedBy = "carBrand", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CarModel> carModels;

    public CarBrand(){

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

    public Set<CarModel> getCarModels() {
        return carModels;
    }

    public void setCarModels(Set<CarModel> carModels) {
        this.carModels = carModels;
    }
}
