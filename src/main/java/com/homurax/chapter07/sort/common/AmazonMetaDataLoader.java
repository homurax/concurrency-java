package com.homurax.chapter07.sort.common;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AmazonMetaDataLoader {

    public static AmazonMetaData[] load(Path path) {

        List<AmazonMetaData> list = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(path)) {

            String line;
            while ((line = reader.readLine()) != null) {
                AmazonMetaData item = processItem(line);
                list.add(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        AmazonMetaData[] result = new AmazonMetaData[list.size()];
        return list.toArray(result);
    }

    private static AmazonMetaData processItem(String line) {

        AmazonMetaData item = new AmazonMetaData();

        String[] tokens = line.split(";;");

        if (tokens.length != 8) {
            System.err.println("Error: " + line);
            System.err.println("Tokens: " + tokens.length);
            System.exit(-1);
        }

        item.setId(Integer.parseInt(tokens[0]));
        item.setASIN(tokens[1]);
        item.setTitle(tokens[2]);
        item.setGroup(tokens[3]);
        item.setSalesrank(Long.parseLong(tokens[4]));
        item.setReviews(Integer.parseInt(tokens[5]));
        item.setSimilar(Integer.parseInt(tokens[6]));
        item.setCategories(Integer.parseInt(tokens[7]));

        return item;
    }

}
