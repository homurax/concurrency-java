package com.homurax.chapter11.structure.hash.data;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class BasicProductLoader {

    public static ConcurrentHashMap<BasicProduct, ConcurrentLinkedDeque<BasicReview>> load(Path path) {

        try (BufferedReader reader = Files.newBufferedReader(path)) {

            ConcurrentHashMap<BasicProduct, ConcurrentLinkedDeque<BasicReview>> products = new ConcurrentHashMap<>();

            String line;
            while ((line = reader.readLine()) != null) {

                String[] tokens = line.split("::");
                BasicProduct product = new BasicProduct(tokens[0], tokens[1], tokens[2]);

                String[] tokensReview = tokens[3].split(":");
                BasicReview review = new BasicReview(tokensReview[1], Short.parseShort(tokensReview[2]), tokensReview[0]);

                ConcurrentLinkedDeque<BasicReview> reviews = products.get(product);

                if (reviews == null) {
                    reviews = new ConcurrentLinkedDeque<>();
                    reviews.add(review);
                    products.put(product, reviews);
                } else {
                    reviews.add(review);
                }

            }

            return products;
        } catch (Exception x) {
            x.printStackTrace();
        }
		return null;
    }

}
