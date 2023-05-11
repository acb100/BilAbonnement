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

    public Employee getEmployee(int employeeId){
        return repo.getEmployee(employeeId);
    }
}
