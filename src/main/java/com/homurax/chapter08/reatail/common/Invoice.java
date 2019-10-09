package com.homurax.chapter08.reatail.common;

import lombok.Data;

@Data
public class Invoice {

    private String id;
    private String customerId;
    private double amount;
}
