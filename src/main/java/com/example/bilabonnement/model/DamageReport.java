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
    private int[] damage_type_ids;

    public DamageReport(int damage_report_id, String damage_report_comment, int damage_report_overdriven_km, int employee_id, int[] damage_type_ids) {
        this.damage_report_id = damage_report_id;
        this.damage_report_comment = damage_report_comment;
        this.damage_report_overdriven_km = damage_report_overdriven_km;
        this.employee_id = employee_id;
        this.damage_type_ids = damage_type_ids;
    }


}
