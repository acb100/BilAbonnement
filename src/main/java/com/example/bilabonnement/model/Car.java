package com.example.bilabonnement.model;

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

    public String getModelName(){
        return this.modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getModelBrand() {
        return modelBrand;
    }

    public void setModelBrand(String modelBrand) {
        this.modelBrand = modelBrand;
    }

    public int getCarId() {
        return this.carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getEquipmentLevel() {
        return this.equipmentLevel;
    }

    public void setEquipmentLevel(int equipmentLevel) {
        this.equipmentLevel = equipmentLevel;
    }

    public String getVinNumber() {
        return this.vinNumber;
    }

    public void setVinNumber(String vinNumber) {
        this.vinNumber = vinNumber;
    }

    public double getBasePrice() {
        return this.basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getVat() {
        return this.vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getEmission() {
        return this.emission;
    }

    public void setEmission(double emission) {
        this.emission = emission;
    }

    public int getModelId() {
        return this.modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
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
