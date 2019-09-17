package com.homurax.chapter05.matching.concurrent;

import com.homurax.chapter05.matching.loader.WordsLoader;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ExistBasicConcurrenMain {

    public static void main(String[] args) {
        try {
            List<String> dictionary = WordsLoader.load("src\\main\\java\\com\\homurax\\chapter05\\matching\\data\\UK Advanced Cryptics Dictionary.txt");

            System.out.println("Dictionary Size: " + dictionary.size());

            long startTime = System.currentTimeMillis();
            String word = "stitter";
            if (args.length == 1) {
                word = args[0];
            }
            Boolean result = ExistBasicConcurrentCalculation.existWord(word, dictionary);
            long endTime = System.currentTimeMillis();

            System.out.println("Word: " + word);
            System.out.println("Exist: " + result);
            System.out.println("Execution Time: " + (endTime - startTime));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
