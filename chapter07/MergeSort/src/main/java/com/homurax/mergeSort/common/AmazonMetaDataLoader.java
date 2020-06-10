package com.homurax.mergeSort.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AmazonMetaDataLoader {

    public static AmazonMetaData[] load(Path path) {

        List<AmazonMetaData> list = new ArrayList<>();

        try (InputStream in = Files.newInputStream(path);
             BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {

            String line;
            while ((line = reader.readLine()) != null) {
                AmazonMetaData item = processItem(line);
                list.add(item);
            }
        } catch (Exception x) {
            x.printStackTrace();
        }

        AmazonMetaData[] ret = new AmazonMetaData[list.size()];
        return list.toArray(ret);

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
