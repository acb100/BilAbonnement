package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.model.RentalContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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

    public Employee getEmployeeByUsername(String username){
        String sql = "SELECT * FROM employee WHERE employee_username = ?";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        return template.queryForObject(sql, rowMapper, username);
    }

    public Boolean getEmployee(String employeeUsername, String employeePassword) {
        String sql = "SELECT COUNT(*) FROM employee WHERE employee_username = ? AND BINARY employee_password = ?";
        return template.queryForObject(sql, Integer.class,employeeUsername, employeePassword) > 0;
    }
}
