package com.example.bilabonnement.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @NoArgsConstructor
public class RentalContract {
    private int rental_contract_id;
    private Date start_date;
    private Date end_date;
    private String subscription_type;
    private int employee_id;
    private int customer_id;
    private int car_id;
    private int damage_report_id;
    private boolean ongoing;

    public RentalContract(int rental_contract_id, Date start_date, Date end_date, String subscriptionId, int employee_id,
                          int customer_id, int car_id, int damage_report_id, int discountId, String subscription_type, boolean ongoing) {
        this.rental_contract_id = rental_contract_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.employee_id = employee_id;
        this.customer_id = customer_id;
        this.car_id = car_id;
        this.damage_report_id = damage_report_id;
        this.subscription_type = subscription_type;
        this.ongoing = ongoing;
    }

}
