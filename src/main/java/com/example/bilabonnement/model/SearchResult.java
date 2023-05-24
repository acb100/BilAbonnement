package com.example.bilabonnement.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class SearchResult {
    private List<RentalContract> rentalContracts;
    private List<Car> cars;
    private List<Customer> customers;
    private List<DamageReport> damageReports;


    public SearchResult(List<RentalContract> rentalContracts, List<Car> cars, List<Customer> customers, List<DamageReport> damageReports) {
        this.rentalContracts = rentalContracts;
        this.cars = cars;
        this.customers = customers;
        this.damageReports = damageReports;
    }
}
