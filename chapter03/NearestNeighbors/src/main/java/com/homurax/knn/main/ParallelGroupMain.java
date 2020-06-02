package com.homurax.knn.main;

import com.homurax.knn.data.BankMarketing;
import com.homurax.knn.loader.BankMarketingLoader;
import com.homurax.knn.parallel.group.KnnClassifierParallelGroup;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class ParallelGroupMain {

    public static void main(String[] args) {

        BankMarketingLoader loader = new BankMarketingLoader();
        List<BankMarketing> train = loader.load("data\\bank.data");
        System.out.println("Train: " + train.size());
        List<BankMarketing> test = loader.load("data\\bank.test");
        System.out.println("Test: " + test.size());


        int success = 0, mistakes = 0, k = 10;
        if (args.length == 1) {
            k = Integer.parseInt(args[0]);
        }

        KnnClassifierParallelGroup classifier = new KnnClassifierParallelGroup(train, k, 1, true);
        try {
            LocalDateTime start = LocalDateTime.now();
            for (BankMarketing example : test) {
                String tag = classifier.classify(example);
                if (tag.equals(example.getTag())) {
                    success++;
                } else {
                    mistakes++;
                }
            }
            LocalDateTime end = LocalDateTime.now();
            Duration duration = Duration.between(start, end);

            classifier.destroy();

            System.out.println("******************************************");
            System.out.println("Parallel Classifier Group - K: " + k + " - Factor 1 - Parallel Sort: true");
            System.out.println("Success: " + success);
            System.out.println("Mistakes: " + mistakes);
            System.out.println("Execution Time: " + duration);
            System.out.println("******************************************");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
