package com.example.bilabonnement.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class AdvanceAgreement {
    private int advance_agreement_id;
    private String buyer_name;
    private int buyer_id;
    private String model_name;
    private int car_id;
    private double advance_agreement_price;
    private String advance_agreement_text;

    public AdvanceAgreement(int advance_agreement_id, String buyer_name, int buyer_id, String model_name, int car_id, double advance_agreement_price, String advance_agreement_text) {
        this.advance_agreement_id = advance_agreement_id;
        this.buyer_name = buyer_name;
        this.buyer_id = buyer_id;
        this.model_name = model_name;
        this.car_id = car_id;
        this.advance_agreement_price = advance_agreement_price;
        this.advance_agreement_text = advance_agreement_text;
    }
}
