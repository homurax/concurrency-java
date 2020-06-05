package com.homurax.bestMatching.concurrent;

import com.homurax.bestMatching.data.WordsLoader;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ExistBasicConcurrenMain {

    public static void main(String[] args) {
        try {
            List<String> dictionary = WordsLoader.load("data/UK Advanced Cryptics Dictionary.txt");

            System.out.println("Dictionary Size: " + dictionary.size());

            LocalDateTime start = LocalDateTime.now();

            String word = "stitter";

            if (args.length == 1) {
                word = args[0];
            }

            boolean result = ExistBasicConcurrentCalculation.existWord(word, dictionary);

            LocalDateTime end = LocalDateTime.now();
            Duration duration = Duration.between(start, end);

            System.out.println("Word: " + word);
            System.out.println("Exist: " + result);
            System.out.println("Execution Time: " + duration);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
