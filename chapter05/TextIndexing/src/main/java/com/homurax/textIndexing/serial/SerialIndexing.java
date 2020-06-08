package com.homurax.textIndexing.serial;

import com.homurax.textIndexing.common.DocumentParser;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class SerialIndexing {

    public static void main(String[] args) {

        File source = new File("data");
        File[] files = source.listFiles();
        Map<String, StringBuffer> invertedIndex = new HashMap<>();

        LocalDateTime start = LocalDateTime.now();
        for (File file : files) {
            DocumentParser parser = new DocumentParser();
            if (file.getName().endsWith(".txt")) {
                Map<String, Integer> voc = parser.parse(file.getAbsolutePath());
                updateInvertedIndex(voc, invertedIndex, file.getName());
            }
        }
        LocalDateTime end = LocalDateTime.now();
        Duration duration = Duration.between(start, end);

        System.out.println("Execution Time: " + duration);
        System.out.println("invertedIndex: " + invertedIndex.size());
    }

    private static void updateInvertedIndex(Map<String, Integer> voc,
                                            Map<String, StringBuffer> invertedIndex,
                                            String fileName) {

        for (String word : voc.keySet()) {
            if (word.length() >= 3) {
                invertedIndex.computeIfAbsent(word, k -> new StringBuffer()).append(fileName).append(";");
            }
        }
    }

}
