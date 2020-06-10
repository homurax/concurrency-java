package com.homurax.searchWithoutIndexing.serial.main;

import com.homurax.searchWithoutIndexing.data.Product;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class SerialMainSearch {

    public static void main(String[] args) {
        String query = "java";
        Path file = Paths.get("data");
        try {
            LocalDateTime start = LocalDateTime.now();
            ArrayList<Product> results = Files
                    .walk(file, FileVisitOption.FOLLOW_LINKS)
                    .filter(f -> f.toString().endsWith(".txt"))
                    .collect(ArrayList::new, new SerialObjectAccumulator(query), ArrayList::addAll);
            LocalDateTime end = LocalDateTime.now();

            System.out.println("Resultados");
            System.out.println("*************");
            results.forEach(p -> System.out.println(p.getTitle()));
            System.out.println("Execution Time: " + Duration.between(start, end));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
