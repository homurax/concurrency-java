package com.homurax.filesearch.main;

import com.homurax.filesearch.parallel.group.ParallelGroupFileSearch;
import com.homurax.filesearch.serial.SerialFileSearch;
import com.homurax.filesearch.util.Result;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) {

        File file = new File("C:\\Windows\\");
        String regex = "hosts";

        Result result = new Result();
        LocalDateTime start = LocalDateTime.now();
        SerialFileSearch.searchFiles(file, regex, result);
        LocalDateTime end = LocalDateTime.now();
        Duration duration = Duration.between(start, end);
        System.out.println("Serial Search: Execution Time: " + duration);


        Result parallelResult = new Result();
        start = LocalDateTime.now();
        ParallelGroupFileSearch.searchFiles(file, regex, parallelResult);
        end = LocalDateTime.now();
        duration = Duration.between(start, end);

        System.out.printf("Parallel Group Search: Path: %s%n", parallelResult.getPath());
        System.out.println("Parallel Group Search: Execution Time: " + duration);
    }

}
