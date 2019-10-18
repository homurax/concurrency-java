package com.homurax.chapter11.synchronization.completable;

import com.homurax.chapter11.structure.hash.data.Product;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SearchTask implements Function<List<Product>, List<Product>> {

    private String query;

    public SearchTask(String query) {
        this.query = query;
    }

    @Override
    public List<Product> apply(List<Product> products) {
        System.out.println(LocalDateTime.now() + ": CompletableTask: start");
        List<Product> result = products.stream()
                .filter(product -> product.getTitle().toLowerCase().contains(query))
                .collect(Collectors.toList());
        System.out.println(LocalDateTime.now() + ": CompletableTask: end: " + result.size());
        return result;
    }

}
