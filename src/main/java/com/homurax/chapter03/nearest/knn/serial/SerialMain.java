package com.homurax.chapter03.nearest.knn.serial;

import com.homurax.chapter03.nearest.knn.data.BankMarketing;
import com.homurax.chapter03.nearest.knn.loader.BankMarketingLoader;

import java.util.Date;
import java.util.List;

public class SerialMain {

    public static void main(String[] args) {

        BankMarketingLoader loader = new BankMarketingLoader();
        List<BankMarketing> train = loader.load("src\\main\\java\\com\\homurax\\chapter03\\nearest\\data\\bank.data");
        System.out.println("Train: " + train.size());
        List<BankMarketing> test = loader.load("src\\main\\java\\com\\homurax\\chapter03\\nearest\\data\\bank.test");
        System.out.println("Test: " + test.size());

        double currentTime = 0d;
        int success = 0, mistakes = 0;
        int k = 10;
        if (args.length == 1) {
            k = Integer.parseInt(args[0]);
        }

        KnnClassifier classifier = new KnnClassifier(train, k);
        try {
            long start = System.currentTimeMillis();
            for (BankMarketing example : test) {
                String tag = classifier.classify(example);
                if (tag.equals(example.getTag())) {
                    success++;
                } else {
                    mistakes++;
                }
            }
            currentTime = System.currentTimeMillis() - start;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("******************************************");
        System.out.println("Serial Classifier - K: " + k);
        System.out.println("Success: " + success);
        System.out.println("Mistakes: " + mistakes);
        System.out.println("Execution Time: " + (currentTime / 1000) + " seconds.");
        System.out.println("******************************************");
    }

}
