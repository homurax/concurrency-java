package com.homurax.knn.parallel.individual;

import com.homurax.knn.data.Distance;
import com.homurax.knn.data.Sample;
import com.homurax.knn.distances.EuclideanDistanceCalculator;

import java.util.concurrent.CountDownLatch;

public class IndividualDistanceTask implements Runnable {

    private final Distance[] distances;

    private final int index;

    private final Sample localExample;

    private final Sample example;

    private final CountDownLatch endController;

    public IndividualDistanceTask(Distance[] distances, int index, Sample localExample, Sample example,
                                  CountDownLatch endController) {
        this.distances = distances;
        this.index = index;
        this.localExample = localExample;
        this.example = example;
        this.endController = endController;
    }

    @Override
    public void run() {
        distances[index] = new Distance();
        distances[index].setIndex(index);
        distances[index].setDistance(EuclideanDistanceCalculator.calculate(localExample, example));
        endController.countDown();
    }

}
