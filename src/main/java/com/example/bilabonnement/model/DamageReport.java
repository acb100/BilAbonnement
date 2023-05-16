package com.example.bilabonnement.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class DamageReport {
    private int damage_report_id;
    private String damage_report_comment;
    private int damage_report_overdriven_km;
    private int employee_id;

    public DamageReport(int damage_report_id, String damage_report_comment, int employee_id) {
        this.damage_report_id = damage_report_id;
        this.damage_report_comment = damage_report_comment;
        this.employee_id = employee_id;
    }


}
