package com.homurax.mergeSort.concurrent;

import com.homurax.mergeSort.common.AmazonMetaData;
import com.homurax.mergeSort.common.AmazonMetaDataLoader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

public class ConcurrentMetaData {

    public static void main(String[] args) {

        for (int j = 0; j < 10; j++) {
            Path path = Paths.get("data", "amazon-meta.csv");

            AmazonMetaData[] data = AmazonMetaDataLoader.load(path);
            AmazonMetaData[] data2 = Arrays.copyOf(data, data.length);

            LocalDateTime start = LocalDateTime.now();
            Arrays.parallelSort(data);
            LocalDateTime end = LocalDateTime.now();
            System.out.println("Execution Time Java Arrays.parallelSort(): " + Duration.between(start, end));

            System.out.println(data[0].getTitle());
            System.out.println(data2[0].getTitle());
            ConcurrentMergeSort mySorter = new ConcurrentMergeSort();
            start = LocalDateTime.now();
            mySorter.mergeSort(data2, 0, data2.length);
            end = LocalDateTime.now();

            System.out.println("Execution Time Java ConcurrentMergeSort: " + Duration.between(start, end));

            for (int i = 0; i < data.length; i++) {
                if (data[i].compareTo(data2[i]) != 0) {
                    System.err.println("There's a difference is position " + i);
                    System.exit(-1);
                }
            }

            System.out.println("Both arrays are equal");
        }
    }
}
