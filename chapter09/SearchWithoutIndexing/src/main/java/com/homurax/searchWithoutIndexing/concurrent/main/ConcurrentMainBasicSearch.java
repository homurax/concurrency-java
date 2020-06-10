package com.homurax.searchWithoutIndexing.concurrent.main;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConcurrentMainBasicSearch {

    public static void main(String[] args) {
        String query = "java";
        Path file = Paths.get("data");
        try {
            LocalDateTime start = LocalDateTime.now();
            List<String> results = Files
                    .walk(file, FileVisitOption.FOLLOW_LINKS)
                    .parallel()
                    .filter(f -> f.toString().endsWith(".txt"))
                    .collect(ArrayList::new, new ConcurrentStringAccumulator(query), List::addAll);
            LocalDateTime end = LocalDateTime.now();

            System.out.println("Resultados: " + results.size());
            System.out.println("*************");
            results.forEach(System.out::println);
            System.out.println("Execution Time: " + Duration.between(start, end));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
