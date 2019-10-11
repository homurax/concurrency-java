package com.homurax.chapter09.search.data;

import lombok.Data;

@Data
public class ProductRecommendation implements Comparable<ProductRecommendation> {

    private String title;
    private double value;

    public ProductRecommendation(String title, double value) {
        this.title = title;
        this.value = value;
    }

    @Override
    public int compareTo(ProductRecommendation o) {
        return Double.compare(o.getValue(), this.getValue());
    }
}
