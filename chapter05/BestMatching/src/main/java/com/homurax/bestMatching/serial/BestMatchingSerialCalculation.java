package com.homurax.bestMatching.serial;

import com.homurax.bestMatching.common.BestMatchingData;
import com.homurax.bestMatching.distance.LevenshteinDistance;

import java.util.ArrayList;
import java.util.List;

public class BestMatchingSerialCalculation {

    public static BestMatchingData getBestMatchingWords(String word, List<String> dictionary) {

        List<String> results = new ArrayList<>();

        int minDistance = Integer.MAX_VALUE;
        int distance;
        for (String str : dictionary) {
            distance = LevenshteinDistance.calculate(word, str);
            if (distance < minDistance) {
                results.clear();
                minDistance = distance;
                results.add(str);
            } else if (distance == minDistance) {
                results.add(str);
            }
        }

        return new BestMatchingData(minDistance, results);
    }

}
