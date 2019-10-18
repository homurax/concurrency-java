package com.homurax.chapter11.synchronization.completable;

import com.homurax.chapter11.structure.hash.data.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CompletableTask implements Function<List<Product>, List<Product>> {

    private String query;

    public CompletableTask(String query) {
        this.query = query;
    }

    @Override
    public List<Product> apply(List<Product> products) {
        List<Product> result = new ArrayList<>();
        System.out.println(LocalDateTime.now() + ": CompletableTask: start");
        products.forEach(product -> {
            if (product.getTitle().toLowerCase().contains(query)) {
				result.add(product);
            }
        });
        System.out.println(LocalDateTime.now() + ": CompletableTask: end: " + result.size());
        return result;
    }

}
