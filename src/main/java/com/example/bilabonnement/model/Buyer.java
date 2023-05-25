package com.example.bilabonnement.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Buyer {
    private int buyer_id;
    private String buyer_name;

    public Buyer(int buyer_id, String buyer_name){
        this.buyer_id = buyer_id;
        this.buyer_name = buyer_name;
    }
}
