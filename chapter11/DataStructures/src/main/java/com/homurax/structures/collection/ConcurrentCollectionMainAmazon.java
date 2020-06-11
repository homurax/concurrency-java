package com.homurax.structures.collection;

import com.homurax.structures.hash.data.ConcurrentLoaderAccumulator;
import com.homurax.structures.hash.data.Product;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentCollectionMainAmazon {

    public static void main(String[] args) {

        Path file = Paths.get("data\\category");

        ConcurrentLinkedDeque<Product> productList;
        try {
            productList = Files.walk(file, FileVisitOption.FOLLOW_LINKS).parallel()
                    .filter(f -> f.toString().endsWith(".txt"))
                    .collect(ConcurrentLinkedDeque::new,
                            new ConcurrentLoaderAccumulator(),
                            ConcurrentLinkedDeque::addAll);


            System.out.println("Products: " + productList.size());
            productList.removeIf(product -> product.getSalesrank() > 1000);
            System.out.println("Products; " + productList.size());
            productList.forEach(product -> System.out.println(product.getTitle() + ": " + product.getSalesrank()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
