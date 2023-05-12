package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.model.RentalContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

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
    public List<RentalContract> fetchAllRentalContractsForEmployee(int employeeId){
        String sql = "SELECT * FROM rental_contract WHERE employee_id = ?";
        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        return template.query(sql, rowMapper, employeeId);
    }
    public Boolean deleteRentalContract(int rentalContractId){
        String sql = "DELETE FROM rental_contract WHERE rental_contract_id = ?";
        return template.update(sql, rentalContractId) > 0;
    }
    public RentalContract findRentalContractById(int rentalContractId){
        String sql = "SELECT * FROM rental_contract WHERE rental_contract_id = ? OR start_date = ? OR costumer_id = ?";
        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        return template.queryForObject(sql, rowMapper, rentalContractId);
    }
}
