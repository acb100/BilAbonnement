package com.example.bilabonnement.service;


import com.example.bilabonnement.model.*;
import com.example.bilabonnement.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    Repository repo;
    public void addRentalContract(RentalContract rentalContract){
        repo.addRentalContract(rentalContract, repo.addDamageReport());
    }

    public List<DamageType> getAllDamageTypes(){
        return repo.getAllDamageTypes();
    }

    public int getEmployeeID(String username){
        return repo.getEmployee(username).getEmployee_id();
    }

    public Employee getEmployee(String username){
        return repo.getEmployee(username);
    }

    public Boolean employeeExists(String employeeUsername, String employeePassword) {
        return repo.employeeExists(employeeUsername, employeePassword);
    }

    public List<Customer> getAllCustomers(){
        return repo.getAllCustomers();
    }

    public List<Car> getAllCars(){
        return repo.getAllCars();
    }

    public List<Car> getAllUsedCars() { return repo.getAllUsedCars(); }

    public List<Car> getAllUnusedCars() { return repo.getAllUnusedCars(); }

    public List<CarWithCount> getAllCarModels() {
        List<Car> carModels = repo.getAllCarModels();
        List<CarWithCount> carsWithCount = new ArrayList<>();

        for (Car car : carModels) {
            String combinedModel = car.getBrand_name() + " " + car.getModel_name();
            CarWithCount carWithCount = new CarWithCount(combinedModel, car.getModel_count());
            carsWithCount.add(carWithCount);
        }
        return carsWithCount;
    }

    public int getAllUnusedCarRows(){
        return repo.countAllUnusedCarRows();
    }

    public int getAllUsedCarRows(){
        return repo.countAllUsedCarRows();
    }

    public List<RentalContract> getAllRentalContracts(){
        return repo.getAllRentalContracts();
    }

    public List<RentalContract> fetchAllRentalContractsForEmployee(int employeeId){
        return repo.fetchAllRentalContractsForEmployee(employeeId);
    }
    public Boolean deleteRentalContract(int rentalContractId){
        return repo.deleteRentalContract(rentalContractId);
    }
    public RentalContract findRentalContractById(int rentalContractId){
        return repo.findRentalContractById(rentalContractId);
    }

    public void updateDamageReport(DamageReport damageReport, int rentalContractId) {
        repo.updateDamageReport(damageReport, rentalContractId);
        repo.addDamageOnReport(damageReport, rentalContractId);
    }
    public List<Car> fetchAllCars(){
        return repo.fetchAllCars();
    }
}
