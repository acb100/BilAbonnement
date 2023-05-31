package com.example.bilabonnement.repository;

import com.example.bilabonnement.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HomeRepository {

    @Autowired
    JdbcTemplate template;

    /**
     * repository method for adding a rental contract to the database with the SQL insert statement
     * where the subscription id is bound to the subscription type
     * @param rentalContract the rental contract object to be added
     * @param damage_report_id the id for the beloning damage report
     */
    public void addRentalContract(RentalContract rentalContract, int damage_report_id) {
        String sql = "INSERT INTO rental_contract (start_date, end_date, ongoing, employee_id," +
                "subscription_id, customer_id, car_id, damage_report_id) VALUES(?,?,?,?," +
                "(SELECT subscription_id FROM subscription WHERE subscription_type = ?)" +
                ",?,?,?)";
        template.update(sql, rentalContract.getStart_date(), rentalContract.getEnd_date(),
                true, rentalContract.getEmployee_id(), rentalContract.getSubscription_type(), rentalContract.getCustomer_id(),
                rentalContract.getCar_id(), damage_report_id);

    }

    /**
     * repository method for adding a car to the database
     * @param car the car object to be added using the passed values
     */
    public void addCar(Car car) {
        String sql = "INSERT INTO car (vin_nr, equipment_level, base_price, vat," +
                "emission, model_id) VALUES(?,?,?,?,?,?)";
        System.out.println("Vin_nr: " + car.getVin_nr());
        template.update(sql, car.getVin_nr(), car.getEquipment_level(), car.getBase_price(),
                car.getVat(), car.getEmission(), car.getModel_id());
    }

    /**
     * repository method for deleting a car by id
     * @param car_id the passed car id
     * @return returns true if car is deleted
     */
    public Boolean deleteCar(int car_id) {
        String sql = "DELETE FROM car WHERE car_id = ?";
        return template.update(sql, car_id) > 0;
    }

    /**
     * repository method for adding a damage report
     * @return inserts the damage report into the table with the passed values
     */
    public int addDamageReport() {
        String sql = "INSERT INTO damage_report(damage_report_comment) VALUES(?)";
        template.update(sql, "No content");
        String sql2 = "SELECT damage_report_id FROM damage_report ORDER BY damage_report_id DESC LIMIT 1";
        return template.queryForObject(sql2, Integer.class);
    }

    /**
     *
     * @param damageReport
     * @param rentalContractId
     */
    public void addDamageOnReport(DamageReport damageReport, int rentalContractId){
        String sql = "INSERT INTO damage_on_report(damage_report_id, damage_type_id) VALUES(" +
                "(SELECT damage_report_id FROM rental_contract WHERE rental_contract_id = ?), ?)";
        for (int i = 0; i < damageReport.getDamage_type_ids().length; i++) {
            template.update(sql,rentalContractId, damageReport.getDamage_type_ids()[i]);
        }
    }

    public void updateDamageReport(DamageReport damageReport, int rentalContractId){
        String sql = "UPDATE damage_report " +
                "SET damage_report_comment = ?, damage_report_overdriven_km = ?, employee_id = ? " +
                "WHERE damage_report_id = (SELECT damage_report_id FROM rental_contract WHERE rental_contract_id = ?)";
        template.update(sql, damageReport.getDamage_report_comment(),
                damageReport.getDamage_report_overdriven_km(), damageReport.getEmployee_id(), rentalContractId);
    }

    public List<DamageType> getAllDamageTypes() {
        String sql = "SELECT * FROM damage_type";
        RowMapper<DamageType> rowMapper = new BeanPropertyRowMapper<>(DamageType.class);
        return template.query(sql, rowMapper);
    }

    /**
     *repository method for the employee from username parameter
     * @param username the passed username parameter
     * @return returns all the values from employee table where username matches and maps them in the BeanPropertyRowMapper
     */
    public Employee getEmployee(String username) {
        String sql = "SELECT * FROM employee WHERE employee_username = ?";
        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<>(Employee.class);
        return template.queryForObject(sql, rowMapper, username);
    }

    /**
     * repository method for finding an existing user
     * @param employeeUsername the passed username parameter
     * @param employeePassword the passed password parameter
     * @return returns a true value, if the username and passwords exist in the same employee row. Else returns false
     */
    public Boolean employeeExists(String employeeUsername, String employeePassword) {
        String sql = "SELECT COUNT(*) FROM employee WHERE employee_username = ? AND BINARY employee_password = ?";
        return template.queryForObject(sql, Integer.class, employeeUsername, employeePassword) > 0;
    }

    /**
     * repository method for fetching all customers and their values
     * @return returns the mapped customers from the beanpropertyrowmapper with customer as class
     */
    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM customer";
        RowMapper<Customer> rowMapper = new BeanPropertyRowMapper<>(Customer.class);
        return template.query(sql, rowMapper);
    }

    /**
     * repository method for fetching all car models
     * @return returns the mapped car models and car brands and groups them by brand, model
     */
    public List<Car> getAllCarModels() {
        String sql = "SELECT b.brand_name, m.model_name, COUNT(*) AS model_count\n" +
                "FROM brand b\n" +
                "JOIN model m ON b.brand_id = m.brand_id\n" +
                "JOIN car c ON m.model_id = c.model_id\n" +
                "GROUP BY b.brand_name, m.model_name;\n";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper);
    }

    /**
     * repository method for getting a list of all used cars
     * @return returns the mapped cars and their values
     */
    public List<Car> getAllUsedCars() {
        String sql = "SELECT car_id, vin_nr, equipment_level, base_price, vat, emission, model_name, brand_name  " +
                "FROM car JOIN model USING (model_id) JOIN brand USING (brand_id) JOIN rental_contract USING (car_id)";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper);
    }

    /**
     * repository method for getting a list of all unused cars
     * @return returns the mapped cars and their values
     */
    public List<Car> getAllUnusedCars() {
        String sql = "SELECT car_id, vin_nr, equipment_level, base_price, vat, emission, model_name, brand_name  " +
                "FROM car JOIN model USING (model_id) JOIN brand USING (brand_id) LEFT JOIN rental_contract USING (car_id) WHERE rental_contract_id IS NULL";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper);
    }

    /**
     * repository medthod for counting all the unused cars
     * @return returns the count for the unused cars, by querying for object with SQL statement and Integer.class to get a number
     */
    public int countAllUnusedCarRows () {
        String sql = "SELECT COUNT(*) AS row_count FROM car JOIN model USING (model_id) JOIN brand USING (brand_id) LEFT JOIN rental_contract USING (car_id) WHERE rental_contract_id IS NULL;";
        return template.queryForObject(sql, Integer.class);
    }

    /**
     * repository medthod for counting all the used cars
     * @return returns the count for the used cars, by querying for object with SQL statement and Integer.class to get a number
     */
    public int countAllUsedCarRows () {
        String sql = "SELECT COUNT(*) AS row_count FROM car JOIN model USING (model_id) JOIN brand USING (brand_id) LEFT JOIN rental_contract USING (car_id) WHERE rental_contract_id;";
        return template.queryForObject(sql, Integer.class);
    }

    /**
     * repository method for getting a list of all the rental contracts
     * @return returns all rental contracts by mapping them in the beanpropertyrowmapper using the rentalcontract class
     */
    public List<RentalContract> getAllRentalContracts() {
        String sql = "SELECT rental_contract_id, start_date, end_date, subscription_type, employee_id, " +
                "customer_id, car_id, damage_report_id, ongoing " +
                "FROM rental_contract " +
                "JOIN subscription USING (subscription_id) " +
                "ORDER BY rental_contract_id";
        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        return template.query(sql, rowMapper);
    }

    /**
     * repository method for returning a list of cars on the start date of a rental contract
     * @return returns the list of cars as currentdaycars
     */
    public List<CurrentDayCars> getAllCurrentDayContractCars() {
        String sql = "SELECT rental_contract.rental_contract_id, rental_contract.start_date, rental_contract.end_date," +
                " car.vin_nr, brand.brand_name, email, customer_name, model_name, customer_id\n" +
                "FROM rental_contract\n" +
                "JOIN car ON rental_contract.car_id = car.car_id\n" +
                "JOIN customer USING (customer_id)\n" +
                "JOIN model ON car.model_id = model.model_id\n" +
                "JOIN brand ON model.brand_id = brand.brand_id\n" +
                "WHERE rental_contract.start_date = CURDATE();";
        RowMapper<CurrentDayCars> rowMapper = new BeanPropertyRowMapper<>(CurrentDayCars.class);
        return template.query(sql, rowMapper);
    }

    /**
     * repository method for deleing rental contracts using contract id
     * @param rentalContractId the passed rental contract id parameter
     * @return returns true if successful
     */
    public Boolean deleteRentalContract(int rentalContractId) {
        String sql = "DELETE FROM rental_contract WHERE rental_contract_id = ?";
        return template.update(sql, rentalContractId) > 0;
    }

    /**
     * repository method for fetching all cars and their attributes
     * @return returns the list of car objects by parsing them in the beanpropertyrowmapper
     */
    public List<Car> fetchAllCars(){
        String sql = "SELECT ongoing, car_id, vin_nr, equipment_level, base_price, vat, emission," +
                " model_name, brand_name, model_image_url " +
                "FROM car " +
                "LEFT JOIN rental_contract USING(car_id) " +
                "JOIN model USING(model_id) " +
                "JOIN brand USING(brand_id) " +
                "LEFT JOIN model_image USING(model_image_id)" +
                "ORDER BY car_id";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper);
    }

    /**
     * repository method for fetching the price sum of all the currently leased cars
     * @return returns the statement as an Integer
     */
    public int fetchSumOfLeasedCars(){
        String sql ="SELECT SUM(base_price) AS total_price FROM car LEFT JOIN rental_contract USING(car_id) WHERE ongoing = true";
        return template.queryForObject(sql, Integer.class);
    }

    /**
     * repository method for searching for rental contracts
     * @param keyword the passed keyword parameter
     * @return returns the objects where the keyword has a match
     */
    public List<RentalContract> searchRentalContracts(String keyword){
        String sql ="SELECT rental_contract_id, start_date, end_date, subscription_type, employee_id, " +
                "customer_id, car_id, damage_report_id, ongoing " +
                "FROM rental_contract " +
                "JOIN subscription USING(subscription_id) " +
                "WHERE CONCAT_WS(' ', rental_contract_id, start_date, end_date, subscription_type, employee_id," +
                " customer_id, car_id, damage_report_id, ongoing) LIKE CONCAT('%',?,'%')" +
                "ORDER BY rental_contract_id";
        RowMapper<RentalContract> rowMapper = new BeanPropertyRowMapper<>(RentalContract.class);
        return template.query(sql, rowMapper, keyword);
    }
    /**
     * repository method for searching for cars
     * @param keyword the passed keyword parameter
     * @return returns the objects where the keyword has a match
     */
    public List<Car> searchCars(String keyword){
        String sql = "SELECT ongoing, car_id, vin_nr, equipment_level, base_price, vat, emission, " +
                " model_name, brand_name, model_image_url " +
                "FROM car " +
                "LEFT JOIN rental_contract USING(car_id) " +
                "JOIN model USING(model_id) " +
                "JOIN brand USING(brand_id) " +
                "LEFT JOIN model_image USING(model_image_id) " +
                "WHERE CONCAT_WS(' ', ongoing, car_id, vin_nr, equipment_level, base_price, vat, emission," +
                " model_name, brand_name, model_image_url) LIKE CONCAT('%',?,'%')" +
                " ORDER BY car_id";
        RowMapper<Car> rowMapper = new BeanPropertyRowMapper<>(Car.class);
        return template.query(sql, rowMapper, keyword);
    }

    /**
     * repository method for searching for damage reports
     * @param keyword the passed keyword parameter
     * @return returns the objects where the keyword has a match
     */
    public List<DamageReport> searchDamageReports(String keyword){
        String sql ="SELECT damage_report_id, damage_report_comment, damage_report_overdriven_km, employee_id " +
                "FROM damage_report " +
                "WHERE CONCAT_WS(' ', damage_report_id, damage_report_comment, damage_report_overdriven_km," +
                " employee_id) LIKE CONCAT('%',?,'%') " +
                "ORDER BY damage_report_id";
        RowMapper<DamageReport> rowMapper = new BeanPropertyRowMapper<>(DamageReport.class);
        return template.query(sql, rowMapper, keyword);
    }
    /**
     * repository method for searching for everything
     * @param keyword the passed keyword parameter
     * @return returns the searchresult object where the keyword has a match
     */
    public SearchResult searchAll(String keyword){
        SearchResult searchResult = new SearchResult();
        searchResult.setRentalContracts(searchRentalContracts(keyword));
        searchResult.setCars(searchCars(keyword));
        searchResult.setDamageReports(searchDamageReports(keyword));
        return searchResult;
    }

    /**
     * repository method for adding an advance agreement to the database
     * @param advanceAgreement the passed advance agreement object with attributes
     */
    public void addAdvanceAgreement(AdvanceAgreement advanceAgreement){
        String sql = "INSERT INTO advance_agreement (buyer_id, car_id, advance_agreement_price, advance_agreement_text)" +
                " VALUES(?,?,?,?)";
        template.update(sql, advanceAgreement.getBuyer_id(),
                advanceAgreement.getCar_id(), advanceAgreement.getAdvance_agreement_price(),
                advanceAgreement.getAdvance_agreement_text());
    }

    /**
     * repository method for returning a list of all buyers
     * @return returns all rows from buyer using the BeanPropertyRowMapper
     */
    public List<Buyer> getAllBuyers() {
        String sql = "SELECT * FROM buyer";
        RowMapper<Buyer> rowMapper = new BeanPropertyRowMapper<>(Buyer.class);
        return template.query(sql, rowMapper);
    }

    /**
     * repository method for returning a list of all advance agreements and their values
     * @return returns all rows from advance agreements by using the BeanPropertyRowMapper
     */
    public List<AdvanceAgreement> getAllAdvanceAgreements(){
        String sql = "SELECT advance_agreement_id, buyer_name, model_name, advance_agreement_price, advance_agreement_text," +
                " car_id" +
                " FROM advance_agreement" +
                " JOIN car USING(car_id)" +
                " JOIN buyer USING(buyer_id)" +
                " JOIN model USING(model_id)";
        RowMapper<AdvanceAgreement> rowMapper = new BeanPropertyRowMapper<>(AdvanceAgreement.class);
        return template.query(sql, rowMapper);
    }

    /**
     * repository method for deleting an advance agreement by using the id
     * @param advanceAgreementId the passed advance agreement id parameter
     * @return returns true if successful else returns false
     */
    public Boolean deleteAdvanceAgreement(int advanceAgreementId){
        String sql = "DELETE FROM advance_agreement WHERE advance_agreement_id = ?";
        return template.update(sql, advanceAgreementId) > 0;
    }
}
