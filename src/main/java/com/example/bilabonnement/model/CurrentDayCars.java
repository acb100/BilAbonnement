package com.example.bilabonnement.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class CurrentDayCars {
    private int rental_contract_id;
    private String email;
    private String customer_name;
    private String start_date;
    private String end_date;
    private String vin_nr;
    private String brand_name;
    private String model_name;

    public CurrentDayCars(int rental_contract_id, String email, String customer_name, String start_date, String end_date, String vin_nr, String brand_name, String model_name) {
        this.rental_contract_id = rental_contract_id;
        this.email = email;
        this.customer_name = customer_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.vin_nr = vin_nr;
        this.brand_name = brand_name;
        this.model_name = model_name;
    }
}
