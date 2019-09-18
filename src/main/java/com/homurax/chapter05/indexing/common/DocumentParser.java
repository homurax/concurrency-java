package com.homurax.chapter05.indexing.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class DocumentParser {

    public Map<String, Integer> parse(String route) {

        Path file = Paths.get(route);
        Map<String, Integer> result = new HashMap<>();
        try {
            List<String> lines = Files.readAllLines(file);
            lines.forEach(line -> parseLine(line, result));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static final Pattern PATTERN = Pattern.compile("\\P{IsAlphabetic}+");

    private void parseLine(String line, Map<String, Integer> result) {
        for (String word : PATTERN.split(line)) {
            if (!word.isEmpty())
                result.merge(Normalizer.normalize(word, Normalizer.Form.NFKD).toLowerCase(), 1, Integer::sum);
        }
    }

}
