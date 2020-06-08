package com.homurax.geneticAlgorithm.concurrent;

import com.homurax.geneticAlgorithm.common.GeneticOperators;
import com.homurax.geneticAlgorithm.common.Individual;

public class ConcurrentGeneticAlgorithm {

    private final int numberOfGenerations;
    private final int numberOfIndividuals;
    private final int[][] distanceMatrix;
    private final int size;

    public ConcurrentGeneticAlgorithm(int[][] distanceMatrix, int numberOfGenerations, int numberOfIndividuals) {
        this.distanceMatrix = distanceMatrix;
        this.numberOfGenerations = numberOfGenerations;
        this.numberOfIndividuals = numberOfIndividuals;
        this.size = distanceMatrix.length;
    }

    public Individual calculate() {

        Individual[] population = GeneticOperators.initialize(numberOfIndividuals, size);
        GeneticOperators.evaluate(population, distanceMatrix);

        SharedData data = new SharedData();
        data.setPopulation(population);
        data.setDistanceMatrix(distanceMatrix);
        data.setBest(population[0]);

        int numTasks = Runtime.getRuntime().availableProcessors();
        GeneticPhaser phaser = new GeneticPhaser(numTasks, data);

        ConcurrentGeneticTask[] tasks = new ConcurrentGeneticTask[numTasks];
        Thread[] threads = new Thread[numTasks];

        tasks[0] = new ConcurrentGeneticTask(phaser, numberOfGenerations, true);
        for (int i = 1; i < numTasks; i++) {
            tasks[i] = new ConcurrentGeneticTask(phaser, numberOfGenerations, false);
        }

        for (int i = 0; i < numTasks; i++) {
            threads[i] = new Thread(tasks[i]);
            threads[i].start();
        }

        for (int i = 0; i < numTasks; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return data.getBest();
    }
}
