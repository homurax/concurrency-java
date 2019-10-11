package com.homurax.chapter09.search.serial;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SerialMainBasicSearch {

    public static void main(String[] args) {

        String query = "java";
        Path file = Paths.get("data");
        try {
            long start = System.currentTimeMillis();
            ArrayList<String> results = Files
                    .walk(file, FileVisitOption.FOLLOW_LINKS)
					.filter(f -> f.toString().endsWith(".txt"))
					.collect(ArrayList::new, new SerialStringAccumulator(query), ArrayList::addAll);
            long end = System.currentTimeMillis();

            System.out.println("Resultados: " + results.size());
            System.out.println("*************");
            results.forEach(System.out::println);
            System.out.println("Execution Time: " + (end - start));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
