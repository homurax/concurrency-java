package com.homurax.chapter05.matching.serial;

import com.homurax.chapter05.matching.common.BestMatchingData;
import com.homurax.chapter05.matching.loader.WordsLoader;
import java.util.List;

public class BestMatchingSerialMain {

    public static void main(String[] args) {

        List<String> dictionary = WordsLoader.load("src\\main\\java\\com\\homurax\\chapter05\\matching\\data\\UK Advanced Cryptics Dictionary.txt");

        System.out.println("Dictionary Size: " + dictionary.size());

        String word = "stitter";
        if (args.length == 1) {
            word = args[0];
        }

        long startTime, endTime;
        startTime = System.currentTimeMillis();
        boolean result = ExistSerialCalculation.existWord(word, dictionary);
        endTime = System.currentTimeMillis();

        System.out.println("Word: " + word);
        System.out.println("Exists: " + result);
        System.out.println("Execution Time: " + (endTime - startTime));
        System.out.println();

        startTime = System.currentTimeMillis();
        BestMatchingData resultData = BestMatchingSerialCalculation.getBestMatchingWords(word, dictionary);
        List<String> results = resultData.getWords();
        endTime = System.currentTimeMillis();

        System.out.println("Word: " + word);
        System.out.println("Min distance: " + resultData.getDistance());
        System.out.println("List of best matching words: " + results.size());
        results.forEach(System.out::println);
        System.out.println("Execution Time: " + (endTime - startTime));
    }

}
