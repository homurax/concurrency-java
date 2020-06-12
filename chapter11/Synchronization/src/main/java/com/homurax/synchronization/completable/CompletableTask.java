package com.homurax.synchronization.completable;

import com.homurax.synchronization.data.Product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class CompletableTask implements Function<List<Product>, List<Product>> {

    private final String query;

    public CompletableTask(String query) {
        this.query = query;
    }

    @Override
    public List<Product> apply(List<Product> products) {
        List<Product> ret = new ArrayList<>();
        System.out.println(LocalDateTime.now() + ": CompletableTask: start");
        products.forEach(product -> {
            if (product.getTitle().toLowerCase().contains(query)) {
                ret.add(product);
            }
        });
        System.out.println(LocalDateTime.now() + ": CompletableTask: end: " + ret.size());
        return ret;
    }

}
