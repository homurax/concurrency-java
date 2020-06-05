package com.homurax.bestMatching.concurrent;

import com.homurax.bestMatching.common.BestMatchingData;
import com.homurax.bestMatching.distance.LevenshteinDistance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class BestMatchingBasicTask implements Callable<BestMatchingData> {

    private final int startIndex;

    private final int endIndex;

    private final List<String> dictionary;

    private final String word;

    public BestMatchingBasicTask(int startIndex, int endIndex, List<String> dictionary, String word) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.dictionary = dictionary;
        this.word = word;
    }

    @Override
    public BestMatchingData call() throws Exception {
        List<String> results = new ArrayList<>();
        int minDistance = Integer.MAX_VALUE;
        int distance;
        for (int i = startIndex; i < endIndex; i++) {
            distance = LevenshteinDistance.calculate(word, dictionary.get(i));
            if (distance < minDistance) {
                results.clear();
                minDistance = distance;
                results.add(dictionary.get(i));
            } else if (distance == minDistance) {
                results.add(dictionary.get(i));
            }
        }

        return new BestMatchingData(minDistance, results);
    }

}
