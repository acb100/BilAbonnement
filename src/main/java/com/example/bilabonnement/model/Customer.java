package com.example.bilabonnement.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class Customer {
    private int customer_id;
    private String customer_name;
    private String address;
    private String email;
    private String phone_number;
    private int driver_license_number;
    private int cpr_number;

    public Customer(int customer_id, String customer_name, String address, String email, String phone_number, int driver_license_number, int cpr_number, int employeeId) {
        this.customer_id = customer_id;
        this.customer_name = customer_name;
        this.address = address;
        this.email = email;
        this.phone_number = phone_number;
        this.driver_license_number = driver_license_number;
        this.cpr_number = cpr_number;
    }
}
