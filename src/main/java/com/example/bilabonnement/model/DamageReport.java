package com.example.bilabonnement.model;

public class DamageReport {
    private int damageReportId;
    private String damageReportContent;
    private int employeeId;

    public DamageReport(int damageReportId, String damageReportContent, int employeeId) {
        this.damageReportId = damageReportId;
        this.damageReportContent = damageReportContent;
        this.employeeId = employeeId;
    }

    public int getDamageReportId() {
        return this.damageReportId;
    }

    public void setDamageReportId(int damageReportId) {
        this.damageReportId = damageReportId;
    }

    public String getDamageReportContent() {
        return this.damageReportContent;
    }

    public void setDamageReportContent(String damageReportContent) {
        this.damageReportContent = damageReportContent;
    }

    public int getEmployeeId() {
        return this.employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
}
