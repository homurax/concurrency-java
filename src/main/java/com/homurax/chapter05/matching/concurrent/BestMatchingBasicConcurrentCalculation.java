package com.homurax.chapter05.matching.concurrent;

import com.homurax.chapter05.matching.common.BestMatchingData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class BestMatchingBasicConcurrentCalculation {

    public static BestMatchingData getBestMatchingWords(String word, List<String> dictionary) throws InterruptedException, ExecutionException {

        int numCores = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numCores);

        int size = dictionary.size();
        int step = size / numCores;
        int startIndex, endIndex;
        List<Future<BestMatchingData>> futureList = new ArrayList<>();

        for (int i = 0; i < numCores; i++) {
            startIndex = i * step;
            endIndex = (i == numCores - 1) ? dictionary.size() : (i + 1) * step;
            BestMatchingBasicTask task = new BestMatchingBasicTask(startIndex, endIndex, dictionary, word);
            Future<BestMatchingData> future = executor.submit(task);
            futureList.add(future);
        }

        executor.shutdown();

        return merge(futureList);
    }

    public static BestMatchingData getBestMatchingWordsAdvanced(String word, List<String> dictionary) throws InterruptedException, ExecutionException {

        int numCores = Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numCores);

        int size = dictionary.size();
        int step = size / numCores;
        int startIndex, endIndex;
        List<BestMatchingBasicTask> tasks = new ArrayList<>();

        for (int i = 0; i < numCores; i++) {
            startIndex = i * step;
            endIndex = (i == numCores - 1) ? dictionary.size() : (i + 1) * step;
            BestMatchingBasicTask task = new BestMatchingBasicTask(startIndex, endIndex, dictionary, word);
            tasks.add(task);
        }

        List<Future<BestMatchingData>> futureList = executor.invokeAll(tasks);
        executor.shutdown();

        return merge(futureList);
    }

    public static BestMatchingData merge(List<Future<BestMatchingData>> futureList) throws InterruptedException, ExecutionException {

        List<String> words = new ArrayList<>();
        int minDistance = Integer.MAX_VALUE;
        for (Future<BestMatchingData> future : futureList) {
            BestMatchingData data = future.get();
            if (data.getDistance() < minDistance) {
                words.clear();
                minDistance = data.getDistance();
                words.addAll(data.getWords());
            } else if (data.getDistance() == minDistance) {
                words.addAll(data.getWords());
            }
        }

        return new BestMatchingData(minDistance, words);
    }



}
