package com.example.bilabonnement.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Car {
    private int car_id;
    private int equipment_level;
    private String vin_nr;
    private double base_price;
    private double vat;
    private double emission;
    private String model_name;
    private String brand_name;

    public Car(int car_id, int equipment_level, String vin_nr, double base_price, double vat, double emission, int modelId, String model_name, String modelBrand, int brandId, String brand_name) {
        this.car_id = car_id;
        this.equipment_level = equipment_level;
        this.vin_nr = vin_nr;
        this.base_price = base_price;
        this.vat = vat;
        this.emission = emission;
        this.model_name = model_name;
        this.brand_name = brand_name;
    }

    @Override
    public String toString() {
        return "{" +
                " carId='" + getCar_id() + "'" +
                ", equipmentLevel='" + getEquipment_level() + "'" +
                ", vinNumber='" + getVin_nr() + "'" +
                ", basePrice='" + getBase_price() + "'" +
                ", vat='";
    }
}
