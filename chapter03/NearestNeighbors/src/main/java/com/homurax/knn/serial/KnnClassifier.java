package com.homurax.knn.serial;

import com.homurax.knn.data.Distance;
import com.homurax.knn.data.Sample;
import com.homurax.knn.distances.EuclideanDistanceCalculator;

import java.util.*;

public class KnnClassifier {

    private final List<? extends Sample> dataSet;

    private int k;

    public KnnClassifier(List<? extends Sample> dataSet, int k) {
        this.dataSet = dataSet;
        this.k = k;
    }


    public String classify(Sample example) {

        Distance[] distances = new Distance[dataSet.size()];

        int index = 0;

        for (Sample localExample : dataSet) {
            distances[index] = new Distance();
            distances[index].setIndex(index);
            distances[index].setDistance(EuclideanDistanceCalculator.calculate(localExample, example));
            index++;
        }

        Arrays.sort(distances);

        Map<String, Integer> results = new HashMap<>();
        for (int i = 0; i < k; i++) {
            Sample localExample = dataSet.get(distances[i].getIndex());
            String tag = localExample.getTag();
            results.merge(tag, 1, Integer::sum);
        }

        return Collections.max(results.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
