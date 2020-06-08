package com.homurax.geneticAlgorithm.concurrent;

import com.homurax.geneticAlgorithm.common.Individual;
import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class SharedData {

    private Individual[] population;
    private Individual[] selected;
    private AtomicInteger index;
    private Individual best;
    private int[][] distanceMatrix;

    public SharedData() {
        this.index = new AtomicInteger();
    }
}
