package com.homurax.chapter11.structure.collection;

import com.homurax.chapter11.structure.hash.data.ConcurrentLoaderAccumulator;
import com.homurax.chapter11.structure.hash.data.Product;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ConcurrentLinkedDeque;

public class ConcurrentCollectionMainAmazon {

    public static void main(String[] args) {

        Path file = Paths.get("data\\category");

        try {
			ConcurrentLinkedDeque<Product> productList = Files.walk(file, FileVisitOption.FOLLOW_LINKS)
					.parallel()
                    .filter(f -> f.toString().endsWith(".txt"))
					.collect(ConcurrentLinkedDeque::new, new ConcurrentLoaderAccumulator(), ConcurrentLinkedDeque::addAll);


            System.out.println("Products: " + productList.size());

            productList.removeIf(product -> product.getSalesRank() > 1000);

            System.out.println("Products; " + productList.size());

            productList.forEach(product -> System.out.println(product.getTitle() + ": " + product.getSalesRank()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
