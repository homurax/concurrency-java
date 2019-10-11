package com.homurax.chapter09.search.serial;

import com.homurax.chapter09.search.data.Product;
import com.homurax.chapter09.search.data.ProductLoader;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.function.BiConsumer;

public class SerialObjectAccumulator implements BiConsumer<ArrayList<Product>, Path> {

    private String word;

    public SerialObjectAccumulator(String word) {
        this.word = word;
    }

    @Override
    public void accept(ArrayList<Product> list, Path path) {

        Product product = ProductLoader.load(path);

        if (product.getTitle().toLowerCase().contains(word.toLowerCase())) {
            list.add(product);
        }
    }

}
