package com.example.bilabonnement.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
public class CarWithCount {
    private String model_name;
    private int model_count;

    public CarWithCount(String model_name, int model_count) {
        this.model_name = model_name;
        this.model_count = model_count;
    }
}

