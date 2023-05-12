package com.example.bilabonnement.service;


import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.model.RentalContract;
import com.example.bilabonnement.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    Repository repo;
    public void addRentalContract(RentalContract rentalContract){
        repo.addRentalContract(rentalContract);
    }

    public int getEmployeeID(String username){
        return repo.getEmployeeByUsername(username).getEmployeeId();
    }

    public Employee getEmployee(String username){
        return repo.getEmployeeByUsername(username);
    }

    public Boolean getEmployee(String employeeUsername, String employeePassword) {
        return repo.getEmployee(employeeUsername, employeePassword);
    }
}
