package com.homurax.chapter07.sort.concurrent;

import com.homurax.chapter07.sort.common.AmazonMetaData;
import com.homurax.chapter07.sort.common.AmazonMetaDataLoader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class ConcurrentMetaData {

    public static void main(String[] args) {

        for (int j = 0; j < 1; j++) {

            Path path = Paths.get("src\\main\\java\\com\\homurax\\chapter07\\sort\\data", "amazon-meta.csv");

            AmazonMetaData[] data = AmazonMetaDataLoader.load(path);
            AmazonMetaData[] data2 = Arrays.copyOf(data, data.length);

            long start, end;

            start = System.currentTimeMillis();
            Arrays.parallelSort(data);
            end = System.currentTimeMillis();
            System.out.println("Execution Time Java Arrays.parallelSort(): " + (end - start));

            System.out.println(data[0].getTitle());
            System.out.println(data2[0].getTitle());

            ConcurrentMergeSort mySorter = new ConcurrentMergeSort();
            start = System.currentTimeMillis();
            mySorter.mergeSort(data2, 0, data2.length);
            end = System.currentTimeMillis();

            System.out.println("Execution Time Java ConcurrentMergeSort: " + (end - start));

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
