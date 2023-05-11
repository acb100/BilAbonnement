package com.example.bilabonnement.model;

import java.util.Date;

public class RentalContract {
    private int rentalContractId;
    private Date startDate;
    private Date endDate;
    private String subscriptionId;
    private String subscriptionType;
    private int employeeId;
    private int customerId;
    private int carId;
    private int damageReportId;
    private int discountId;
    private boolean isOngoing;

    public RentalContract(int rentalContractId, Date startDate, Date endDate, String subscriptionId, int employeeId,
                          int customerId, int carId, int damageReportId, int discountId, String subscriptionType, boolean isOngoing) {
        this.rentalContractId = rentalContractId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.subscriptionId = subscriptionId;
        this.employeeId = employeeId;
        this.customerId = customerId;
        this.carId = carId;
        this.damageReportId = damageReportId;
        this.discountId = discountId;
        this.subscriptionType = subscriptionType;
        this.isOngoing = isOngoing;
    }
    public boolean isOngoing() {
        return isOngoing;
    }

    public void setOngoing(boolean ongoing) {
        isOngoing = ongoing;
    }

    public int getRentalContractId() {
        return this.rentalContractId;
    }

    public void setRentalContractId(int rentalContractId) {
        this.rentalContractId = rentalContractId;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getDamageReportId() {
        return damageReportId;
    }

    public void setDamageReportId(int damageReportId) {
        this.damageReportId = damageReportId;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
}
