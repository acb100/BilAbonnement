package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.RentalContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@org.springframework.stereotype.Repository
public class Repository {

    @Autowired
    JdbcTemplate template;
    public void addRentalContract(RentalContract rentalContract){
    String sql = "INSERT INTO rental_contract (rental_contract_id, start_date, end_date, ongoing, employee_id," +
             "subscription_id, customer_id, car_id, damage_report_id) VALUES(?,?,?,?,?,?,?,?,?)";
    template.update(sql, rentalContract.getRentalContractId(), rentalContract.getStartDate(), rentalContract.getEndDate(),
            true, rentalContract.getEmployeeId(), rentalContract.getSubscriptionId(), rentalContract.getCustomerId(),
            rentalContract.getCarId(), rentalContract.getDamageReportId());

    }
}
