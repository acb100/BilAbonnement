package com.example.bilabonnement.service;


import com.example.bilabonnement.model.Employee;
import com.example.bilabonnement.model.RentalContract;
import com.example.bilabonnement.repository.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class Service {

    @Autowired
    Repository repo;
    public void addRentalContract(RentalContract rentalContract){
        repo.addRentalContract(rentalContract);
    }

    public Employee getEmployeeByID(int employeeId){
        return repo.getEmployeeByID(employeeId);
    }

    public Employee getEmployeeByUsername(String username){
        return repo.getEmployeeByUsername(username);
    }

    public Boolean getEmployee(String employeeUsername, String employeePassword) {
        return repo.getEmployee(employeeUsername, employeePassword);
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
