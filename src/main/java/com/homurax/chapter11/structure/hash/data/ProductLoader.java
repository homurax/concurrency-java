package com.homurax.chapter11.structure.hash.data;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class ProductLoader {

    public static Product load(Path path) {

        try (BufferedReader reader = Files.newBufferedReader(path)) {

            Product product = new Product();
            String line = reader.readLine();
            product.setId(line.split(":")[1]);

            line = reader.readLine();
            product.setAsin(line.split(":")[1]);

            line = reader.readLine();
            product.setTitle(line.split(":")[1]);

            line = reader.readLine();
            product.setGroup(line.split(":")[1]);

            line = reader.readLine();
            product.setSalesRank(Long.parseLong(line.split(":")[1]));

            line = reader.readLine();
            product.setSimilar(line.split(":")[1]);

            line = reader.readLine();
            int numItems = Integer.parseInt(line.split(":")[1]);
            for (int i = 0; i < numItems; i++) {
                line = reader.readLine();
                product.addCategory(line.split(":")[1]);
            }

            line = reader.readLine();
            numItems = Integer.parseInt(line.split(":")[1]);
            for (int i = 0; i < numItems; i++) {
                line = reader.readLine();
                String[] tokens = line.split(":");
                Review review = new Review(tokens[1], Short.parseShort(tokens[2]));
                product.addReview(review);
            }
            return product;

        } catch (Exception x) {
            x.printStackTrace();
        }
        return null;
    }

}
