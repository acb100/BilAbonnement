package com.example.bilabonnement.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class Car {
    private int car_id;
    private int equipment_level;
    private String vin_nr;
    private double base_price;
    private double vat;
    private double emission;
    private String model_name;
    private String brand_name;

    private Boolean ongoing;

    public Car(int car_id, int equipment_level, String vin_nr, double base_price, double vat, double emission, int modelId, String model_name, String modelBrand, int brandId, String brand_name, boolean ongoing) {
        this.car_id = car_id;
        this.equipment_level = equipment_level;
        this.vin_nr = vin_nr;
        this.base_price = base_price;
        this.vat = vat;
        this.emission = emission;
        this.model_name = model_name;
        this.brand_name = brand_name;
        this.ongoing = ongoing;
    }
}
