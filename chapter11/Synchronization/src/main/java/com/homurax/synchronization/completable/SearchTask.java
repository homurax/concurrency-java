package com.homurax.synchronization.completable;

import com.homurax.synchronization.data.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SearchTask implements Function<List<Product>, List<Product>> {

    private final String query;

    public SearchTask(String query) {
        this.query = query;
    }

    @Override
    public List<Product> apply(List<Product> products) {
        System.out.println(LocalDateTime.now() + ": CompletableTask: start");
        List<Product> ret = products.stream()
                .filter(product -> product.getTitle().toLowerCase().contains(query))
                .collect(Collectors.toList());
        System.out.println(LocalDateTime.now() + ": CompletableTask: end: " + ret.size());
        return ret;
    }

}
