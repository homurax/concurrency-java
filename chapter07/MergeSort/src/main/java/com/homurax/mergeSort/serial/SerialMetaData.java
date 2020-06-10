package com.homurax.mergeSort.serial;

import com.homurax.mergeSort.common.AmazonMetaData;
import com.homurax.mergeSort.common.AmazonMetaDataLoader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

public class SerialMetaData {

    public static void main(String[] args) {

        Path path = Paths.get("data", "amazon-meta.csv");

        for (int j = 0; j < 10; j++) {
            AmazonMetaData[] data = AmazonMetaDataLoader.load(path);
            AmazonMetaData[] data2 = data.clone();

            LocalDateTime start = LocalDateTime.now();
            Arrays.sort(data);
            LocalDateTime end = LocalDateTime.now();
            System.out.println("Execution Time Java Arrays.sort(): " + Duration.between(start, end));

            System.out.println(data[0].getTitle());
            System.out.println(data2[0].getTitle());

            start = LocalDateTime.now();
            SerialMergeSort.mergeSort(data2, 0, data2.length);
            end = LocalDateTime.now();

            System.out.println("Execution Time Java SerialMergeSort: " + Duration.between(start, end));

            for (int i = 0; i < data.length; i++) {
                if (data[i].compareTo(data2[i]) != 0) {
                    System.err.println("There's a difference is position " + i);
                    System.exit(-1);
                }
            }

            System.out.println("Both arrays are equal");
            System.out.println(data2[0].getTitle() + ": " + data2[0].getSalesrank());
        }

    }
}
