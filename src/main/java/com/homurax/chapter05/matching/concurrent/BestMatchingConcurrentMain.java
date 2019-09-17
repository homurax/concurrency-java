package com.homurax.chapter05.matching.concurrent;

import com.homurax.chapter05.matching.common.BestMatchingData;
import com.homurax.chapter05.matching.loader.WordsLoader;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class BestMatchingConcurrentMain {

    public static void main(String[] args) {
        try {
            List<String> dictionary = WordsLoader.load("src\\main\\java\\com\\homurax\\chapter05\\matching\\data\\UK Advanced Cryptics Dictionary.txt");

            System.out.println("Dictionary Size: " + dictionary.size());

            String word = "stitter";
            if (args.length == 1) {
                word = args[0];
            }

            long startTime = System.currentTimeMillis();
            BestMatchingData bestMatchingData1 = BestMatchingBasicConcurrentCalculation.getBestMatchingWords(word, dictionary);
            long endTime = System.currentTimeMillis();
            out(word, startTime, endTime, bestMatchingData1);


            startTime = System.currentTimeMillis();
            BestMatchingData bestMatchingData2 = BestMatchingBasicConcurrentCalculation.getBestMatchingWordsAdvanced(word, dictionary);
            endTime = System.currentTimeMillis();
            out(word, startTime, endTime, bestMatchingData2);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private static void out(String word, long startTime, long endTime, BestMatchingData bestMatchingData) {

        List<String> results = bestMatchingData.getWords();
        System.out.println("Word: " + word);
        System.out.println("Min distance: " + bestMatchingData.getDistance());
        System.out.println("List of best matching words: " + results.size());
        results.forEach(System.out::println);
        System.out.println("Execution Time: " + (endTime - startTime));
        System.out.println();
    }

}
