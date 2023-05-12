package com.example.bilabonnement.service;


import com.example.bilabonnement.model.Car;
import com.example.bilabonnement.model.Customer;
import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.model.RentalContract;
import com.example.bilabonnement.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    Repository repo;
    public void addRentalContract(RentalContract rentalContract){
        repo.addRentalContract(rentalContract, repo.addDamageReport());
    }

    public int getEmployeeID(String username){
        return repo.getEmployeeByUsername(username).getEmployee_id();
    }

    public Employee getEmployee(String username){
        return repo.getEmployeeByUsername(username);
    }

    public Boolean getEmployee(String employeeUsername, String employeePassword) {
        return repo.getEmployee(employeeUsername, employeePassword);
    }

    public List<Customer> getAllCustomers(){
        return repo.getAllCustomers();
    }

    public List<Car> getAllCars(){
        return repo.getAllCars();
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
}
