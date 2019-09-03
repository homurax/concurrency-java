package com.homurax.chapter02.search;

import com.homurax.chapter02.search.parallel.ParallelGroupFileSearch;
import com.homurax.chapter02.search.serial.SerialFileSearch;
import com.homurax.chapter02.search.util.Result;

import java.io.File;

public class SearchMain {

    public static void main(String[] args) {

        File file = new File("C:\\Windows\\");
        String fileName = "hosts";
        // serial
        long start = System.currentTimeMillis();
        SerialFileSearch.searchFiles(file, fileName, new Result());
        long end = System.currentTimeMillis();
        System.out.printf("Serial Search: Execution Time: %d%n", end - start);

        // parallel
        Result parallelResult = new Result();
        start = System.currentTimeMillis();
        ParallelGroupFileSearch.searchFiles(file, fileName, parallelResult);
        end = System.currentTimeMillis();

        System.out.printf("Parallel Group Search: Path: %s%n", parallelResult.getPath());
        System.out.printf("Parallel Group Search: Execution Time: %d%n", end - start);
    }
}
