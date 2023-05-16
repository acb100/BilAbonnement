package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

@org.springframework.stereotype.Repository
public class Repository {

    @Autowired
    JdbcTemplate template;

    public void addRentalContract(RentalContract rentalContract, int damage_report_id) {
        String sql = "INSERT INTO rental_contract (start_date, end_date, ongoing, employee_id," +
                "subscription_id, customer_id, car_id, damage_report_id) VALUES(?,?,?,?," +
                "(SELECT subscription_id FROM subscription WHERE subscription_type = ?)" +
                ",?,?,?)";
        template.update(sql, rentalContract.getStart_date(), rentalContract.getEnd_date(),
                true, rentalContract.getEmployee_id(), rentalContract.getSubscription_type(), rentalContract.getCustomer_id(),
                rentalContract.getCar_id(), damage_report_id);

    }

    public int addDamageReport() {
        String sql = "INSERT INTO damage_report(damage_report_content) VALUES(?)";
        template.update(sql, "No content");
        String sql2 = "SELECT damage_report_id FROM damage_report ORDER BY damage_report_id DESC LIMIT 1";
        return template.queryForObject(sql2, Integer.class);
    }

    public void updateDamageReport(DamageReport damageReport, int rentalContractId){
        String sql = "UPDATE damage_report " +
                "SET damage_report_comment = ?, damage_report_overdriven_km = ?, employee_id = ? " +
                "WHERE damage_report_id = (SELECT damage_report_id FROM rental_contract WHERE rental_contract_id = ?)";
        template.update(sql, damageReport.getDamage_report_comment(),
                damageReport.getDamage_report_overdriven_km(), damageReport.getEmployee_id(), rentalContractId);
    }

    public Employee getEmployee(String username) {
        String sql = "SELECT * FROM employee WHERE employee_username = ?";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        return template.queryForObject(sql, rowMapper, username);
    }

    public Boolean employeeExists(String employeeUsername, String employeePassword) {
        String sql = "SELECT COUNT(*) FROM employee WHERE employee_username = ? AND BINARY employee_password = ?";
        return template.queryForObject(sql, Integer.class, employeeUsername, employeePassword) > 0;
    }

    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM customer";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return template.query(sql, rowMapper);
    }

    public List<Car> getAllCars() {
        String sql = "SELECT car_id, vin_nr, equipment_level, base_price, vat, emission, model_name, brand_name  " +
                "FROM car JOIN model USING (model_id) JOIN brand USING (brand_id)";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper);
    }

    public List<RentalContract> getAllRentalContracts() {
        String sql = "SELECT rental_contract_id, start_date, end_date, subscription_type, employee_id, " +
                "customer_id, car_id, damage_report_id, ongoing " +
                "FROM rental_contract " +
                "JOIN subscription USING (subscription_id) " +
                "ORDER BY rental_contract_id";
        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        return template.query(sql, rowMapper);
    }

    public List<RentalContract> fetchAllRentalContractsForEmployee(int employeeId) {
        String sql = "SELECT * FROM rental_contract WHERE employee_id = ?";
        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        return template.query(sql, rowMapper, employeeId);
    }

    public Boolean deleteRentalContract(int rentalContractId) {
        String sql = "DELETE FROM rental_contract WHERE rental_contract_id = ?";
        return template.update(sql, rentalContractId) > 0;
    }

    public RentalContract findRentalContractById(int rentalContractId) {
        String sql = "SELECT * FROM rental_contract WHERE rental_contract_id = ? OR start_date = ? OR costumer_id = ?";
        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        return template.queryForObject(sql, rowMapper, rentalContractId);
    }
}
