package com.homurax.chapter09.search.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Product {

    private String id;
    private String asin;
    private String title;
    private String group;
    private long salesRank;
    private String similar;
    private List<String> categories;
    private List<Review> reviews;


    public Product() {
        this.categories = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

    public void addCategory(String category) {
        this.categories.add(category);
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }
}
