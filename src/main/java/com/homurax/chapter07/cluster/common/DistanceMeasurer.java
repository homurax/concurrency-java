package com.homurax.chapter07.cluster.common;

public class DistanceMeasurer {

    public static double euclideanDistance(Word[] words, double[] centroid) {

        double distance = 0;
        int wordIndex = 0;

        for (int i = 0; i < centroid.length; i++) {
            Word word;
            if (wordIndex < words.length && ((word = words[wordIndex]).getIndex() == i)) {
                distance += Math.pow(word.getTfidf() - centroid[i], 2);
                wordIndex++;
            } else {
                distance += centroid[i] * centroid[i];
            }
        }

        return Math.sqrt(distance);
    }

}
