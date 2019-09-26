package com.homurax.chapter07.sort.serial;

import com.homurax.chapter07.sort.common.AmazonMetaData;
import com.homurax.chapter07.sort.common.AmazonMetaDataLoader;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class SerialMetaData {

    public static void main(String[] args) {

        Path path = Paths.get("src\\main\\java\\com\\homurax\\chapter07\\sort\\data", "amazon-meta.csv");

        for (int j = 0; j < 10; j++) {
			
            AmazonMetaData[] data = AmazonMetaDataLoader.load(path);
            AmazonMetaData[] data2 = data.clone();

            long start, end;

            start = System.currentTimeMillis();
            Arrays.sort(data);
            end = System.currentTimeMillis();

            System.out.println("Execution Time Java Arrays.sort(): " + (end - start));

            System.out.println(data[0].getTitle());
            System.out.println(data2[0].getTitle());
            SerialMergeSort mySorter = new SerialMergeSort();
            start = System.currentTimeMillis();
            mySorter.mergeSort(data2, 0, data2.length);
            end = System.currentTimeMillis();

            System.out.println("Execution Time Java SerialMergeSort: " + (end - start));

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
