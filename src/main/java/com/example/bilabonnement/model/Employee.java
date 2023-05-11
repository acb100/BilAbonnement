package com.example.bilabonnement.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Employee {
    private int employeeId;
    private String employeeName;
    private String employeeUsername;
    private String employeePassword;
    private String employeeType;
    private int employeeTypeId;


    public Employee(int employeeId, String employeeName, String employeeUsername, String employeePassword, String employeeType, int employeeTypeId) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeUsername = employeeUsername;
        this.employeePassword = employeePassword;
        this.employeeType = employeeType;
        this.employeeTypeId = employeeTypeId;
    }

}