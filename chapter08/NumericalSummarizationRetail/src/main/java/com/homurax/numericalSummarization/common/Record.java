package com.homurax.numericalSummarization.common;

import lombok.Data;

@Data
public class Record {

    private String id;
    private String stockCode;
    private String description;
    private int quantity;
    private String date;
    private Double unitPrice;
    private String customer;
    private String country;

    public Record(String[] data) {
        this.id = data[0];
        this.stockCode = data[1];
        this.description = data[2];
        this.quantity = Integer.parseInt(data[3]);
        this.date = data[4];
        this.unitPrice = Double.valueOf(data[5]);
        this.customer = data[6];
        this.country = data[7];
    }
}
