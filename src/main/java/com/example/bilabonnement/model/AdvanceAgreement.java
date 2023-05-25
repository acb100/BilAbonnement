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
    private int buyer_id;
    private int car_id;
    private double advance_agreement_price;
    private String advance_agreement_text;

    public AdvanceAgreement(int advance_agreement_id, int buyer_id, int car_id, double advance_agreement_price, String advance_agreement_text) {
        this.advance_agreement_id = advance_agreement_id;
        this.buyer_id = buyer_id;
        this.car_id = car_id;
        this.advance_agreement_price = advance_agreement_price;
        this.advance_agreement_text = advance_agreement_text;
    }
}
