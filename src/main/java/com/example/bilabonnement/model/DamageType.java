package com.example.bilabonnement.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class DamageType {
    private int damage_type_id;
    private String damage_type_name;
    private double damage_type_price;

    public DamageType(int damage_type_id, String damage_type_name, double damage_type_price) {
        this.damage_type_id = damage_type_id;
        this.damage_type_name = damage_type_name;
        this.damage_type_price = damage_type_price;
    }
}
