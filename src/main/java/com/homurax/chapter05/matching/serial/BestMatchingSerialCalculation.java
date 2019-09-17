package com.homurax.chapter05.matching.serial;

import com.homurax.chapter05.matching.common.BestMatchingData;
import com.homurax.chapter05.matching.distance.LevenshteinDistance;

import java.util.ArrayList;
import java.util.List;

public class BestMatchingSerialCalculation {

    public static BestMatchingData getBestMatchingWords(String word, List<String> dictionary) {

        List<String> results = new ArrayList<>();
        int distance, minDistance = Integer.MAX_VALUE;

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
