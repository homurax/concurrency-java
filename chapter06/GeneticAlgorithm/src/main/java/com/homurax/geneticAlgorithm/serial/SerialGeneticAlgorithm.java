package com.homurax.geneticAlgorithm.serial;

import com.homurax.geneticAlgorithm.common.GeneticOperators;
import com.homurax.geneticAlgorithm.common.Individual;

public class SerialGeneticAlgorithm {

    private final int[][] distanceMatrix;

    private final int numberOfGenerations;
    private final int numberOfIndividuals;

    private final int size;

    public SerialGeneticAlgorithm(int[][] distanceMatrix, int numberOfGenerations, int numberOfIndividuals) {
        this.distanceMatrix = distanceMatrix;
        this.numberOfGenerations = numberOfGenerations;
        this.numberOfIndividuals = numberOfIndividuals;
        this.size = distanceMatrix.length;
    }

    public Individual calculate() {

        Individual[] population = GeneticOperators.initialize(numberOfIndividuals, size);
        GeneticOperators.evaluate(population, distanceMatrix);
        Individual best = population[0];

        for (int i = 1; i <= numberOfGenerations; i++) {
            Individual[] selected = GeneticOperators.selection(population);
            population = GeneticOperators.crossover(selected, numberOfIndividuals, size);
            GeneticOperators.evaluate(population, distanceMatrix);
            if (population[0].getValue() < best.getValue()) {
                best = population[0];
            }
        }

        return best;
    }

}
