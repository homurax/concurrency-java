package com.homurax.geneticAlgorithm.concurrent;

import com.homurax.geneticAlgorithm.common.GeneticOperators;
import com.homurax.geneticAlgorithm.common.Individual;

import java.util.Random;

public class ConcurrentGeneticTask implements Runnable {

    private final GeneticPhaser phaser;
    private final SharedData data;
    private final int numberOfGenerations;
    private final boolean main;

    public ConcurrentGeneticTask(GeneticPhaser phaser, int numberOfGenerations,
                                 boolean main) {
        this.phaser = phaser;
        this.numberOfGenerations = numberOfGenerations;
        this.main = main;
        this.data = phaser.getData();
    }

    @Override
    public void run() {

        Random rm = new Random(System.nanoTime());
        for (int i = 0; i < numberOfGenerations; i++) {
            if (main) {
                data.setSelected(GeneticOperators.selection(data.getPopulation()));
            }
            phaser.arriveAndAwaitAdvance();

            // Crossover
            int individualIndex;
            do {
                individualIndex = data.getIndex().getAndAdd(2);
                if (individualIndex < data.getPopulation().length) {
                    int secondIndividual = individualIndex++;

                    int p1Index = rm.nextInt(data.getSelected().length);
                    int p2Index;
                    do {
                        p2Index = rm.nextInt(data.getSelected().length);
                    } while (p1Index == p2Index);

                    Individual parent1 = data.getSelected()[p1Index];
                    Individual parent2 = data.getSelected()[p2Index];
                    Individual individual1 = data.getPopulation()[individualIndex];
                    Individual individual2 = data.getPopulation()[secondIndividual];

                    GeneticOperators.crossover(parent1, parent2, individual1, individual2);
                }
            } while (individualIndex < data.getPopulation().length);
            phaser.arriveAndAwaitAdvance();

            // Evaluation
            do {
                individualIndex = data.getIndex().getAndIncrement();
                if (individualIndex < data.getPopulation().length) {
                    GeneticOperators.evaluate(data.getPopulation()[individualIndex], data.getDistanceMatrix());
                }
            } while (individualIndex < data.getPopulation().length);
            phaser.arriveAndAwaitAdvance();

        }

        phaser.arriveAndDeregister();
    }

}
