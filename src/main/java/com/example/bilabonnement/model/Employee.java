package com.example.bilabonnement.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Employee {
    private int employee_id;
    private String employee_name;
    private String employee_username;
    private String employee_password;
    private String employee_type;
    private int employee_type_id;


    public Employee(int employee_id, String employee_name, String employee_username, String employee_password, String employee_type, int employee_type_id) {
        this.employee_id = employee_id;
        this.employee_name = employee_name;
        this.employee_username = employee_username;
        this.employee_password = employee_password;
        this.employee_type = employee_type;
        this.employee_type_id = employee_type_id;
    }

}