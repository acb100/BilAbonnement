package com.example.bilabonnement.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Car {
    private int carId;
    private int equipmentLevel;
    private String vinNumber;
    private double basePrice;
    private double vat;
    private double emission;
    private int modelId;
    private String modelName;
    private String modelBrand;
    private int brandId;
    private String brandName;

    public Car(int carId, int equipmentLevel, String vinNumber, double basePrice, double vat, double emission, int modelId, String modelName, String modelBrand, int brandId, String brandName) {
        this.carId = carId;
        this.equipmentLevel = equipmentLevel;
        this.vinNumber = vinNumber;
        this.basePrice = basePrice;
        this.vat = vat;
        this.emission = emission;
        this.modelId = modelId;
        this.modelName = modelName;
        this.modelBrand = modelBrand;
        this.brandId = brandId;
        this.brandName = brandName;
    }

    @Override
    public String toString() {
        return "{" +
                " carId='" + getCarId() + "'" +
                ", equipmentLevel='" + getEquipmentLevel() + "'" +
                ", vinNumber='" + getVinNumber() + "'" +
                ", basePrice='" + getBasePrice() + "'" +
                ", vat='";
    }
}
