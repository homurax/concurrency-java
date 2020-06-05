package com.homurax.bestMatching.concurrent;

import com.homurax.bestMatching.common.BestMatchingData;
import com.homurax.bestMatching.data.WordsLoader;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BestMatchingConcurrentAdvancedMain {

    public static void main(String[] args) {
        try {
            List<String> dictionary = WordsLoader.load("data/UK Advanced Cryptics Dictionary.txt");

            System.out.println("Dictionary Size: " + dictionary.size());

            LocalDateTime start = LocalDateTime.now();

            String word = "stitter";
            if (args.length == 1) {
                word = args[0];
            }
            BestMatchingData result = BestMatchingAdvancedConcurrentCalculation.getBestMatchingWords(word, dictionary);
            List<String> results = result.getWords();

            LocalDateTime end = LocalDateTime.now();
            Duration duration = Duration.between(start, end);

            System.out.println("Word: " + word);
            System.out.println("Minimun distance: " + result.getDistance());
            System.out.println("List of best matching words: " + results.size());
            for (String wordOut : results) {
                System.out.println(wordOut);
            }

            System.out.println("Execution Time: " + duration);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
