package com.homurax.structures.hash.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Product {

    private String id;
    private String asin;
    private String title;
    private String group;
    private long salesrank;
    private String similar;
    private List<String> categories;
    private List<Review> reviews;

    public Product() {
        this.categories = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public void addCategory(String category) {
        categories.add(category);
    }

    public void addReview(Review review) {
        reviews.add(review);
    }
}
