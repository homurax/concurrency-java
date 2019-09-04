package com.homurax.chapter03.nearest.knn.parallel.group;

import com.homurax.chapter03.nearest.knn.data.Distance;
import com.homurax.chapter03.nearest.knn.data.Sample;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class KnnClassifierParallelGroup {

    private List<? extends Sample> dataSet;

    private int k;

    private ThreadPoolExecutor executor;

    private int numThreads;

    private boolean parallelSort;

    public KnnClassifierParallelGroup(List<? extends Sample> dataSet, int k, int factor, boolean parallelSort) {
        this.dataSet = dataSet;
        this.k = k;
        this.numThreads = factor * (Runtime.getRuntime().availableProcessors());
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(this.numThreads);
        this.parallelSort = parallelSort;
    }


    public String classify(Sample example) throws Exception {

        Distance[] distances = new Distance[dataSet.size()];
        CountDownLatch endController = new CountDownLatch(numThreads);

        int length = dataSet.size() / numThreads;
        int startIndex = 0, endIndex = length;

        for (int i = 0; i < numThreads; i++) {
            GroupDistanceTask task = new GroupDistanceTask(distances, startIndex, endIndex, dataSet, example, endController);
            startIndex = endIndex;
            endIndex = (i < numThreads - 2) ? endIndex + length : dataSet.size();
            executor.execute(task);
        }

        endController.await();

        if (parallelSort) {
            Arrays.parallelSort(distances);
        } else {
            Arrays.sort(distances);
        }

        Map<String, Integer> results = new HashMap<>();
        for (int i = 0; i < k; i++) {
            Sample localExample = dataSet.get(distances[i].getIndex());
            String tag = localExample.getTag();
            results.merge(tag, 1, Integer::sum);
        }

        return Collections.max(results.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    public void destroy() {
        executor.shutdown();
    }
}
