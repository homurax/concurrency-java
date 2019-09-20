package com.homurax.chapter06.keyword.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;

public class DocumentParser {

    public static Document parse(String path) {

        Document document = new Document();
        Path file = Paths.get(path);
        document.setFileName(file.toString());

        try (BufferedReader reader = Files.newBufferedReader(file)) {
            for (String line : Files.readAllLines(file)) {
                parseLine(line, document);
            }
        } catch (IOException x) {
            x.printStackTrace();
        }

        return document;

    }

    private static void parseLine(String line, Document document) {

        line = Normalizer.normalize(line, Normalizer.Form.NFKD);
        line = line.replaceAll("[^\\p{ASCII}]", "");
        line = line.toLowerCase();

        for (String w : line.split("\\W+")) {
            document.addWord(w);
        }
    }

}
