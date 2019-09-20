package com.homurax.chapter06.genetic.concurrent;

import com.homurax.chapter06.genetic.common.Individual;
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
        index = new AtomicInteger();
    }
}
