package com.homurax.chapter05.indexing.serial;

import com.homurax.chapter05.indexing.common.DocumentParser;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SerialIndexing {

    public static void main(String[] args) {

        File source = new File("src\\main\\java\\com\\homurax\\chapter05\\indexing\\data");
        File[] files = source.listFiles();
        Map<String, StringBuffer> invertedIndex = new HashMap<>();

        long start = System.currentTimeMillis();
        for (File file : files) {
            DocumentParser parser = new DocumentParser();
            if (file.getName().endsWith(".txt")) {
                Map<String, Integer> voc = parser.parse(file.getAbsolutePath());
                updateInvertedIndex(voc, invertedIndex, file.getName());
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("Execution Time: " + (end - start));
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
