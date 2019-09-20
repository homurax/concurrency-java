package com.homurax.chapter06.genetic.serial;

import com.homurax.chapter06.genetic.common.GeneticOperators;
import com.homurax.chapter06.genetic.common.Individual;

public class SerialGeneticAlgorithm {

    private int[][] distanceMatrix;

    private int numberOfGenerations;
    private int numberOfIndividuals;

    private int size;

    public SerialGeneticAlgorithm(int[][] distanceMatrix,
                                  int numberOfGenerations, int numberOfIndividuals) {

        this.distanceMatrix = distanceMatrix;
        this.numberOfGenerations = numberOfGenerations;
        this.numberOfIndividuals = numberOfIndividuals;
        this.size = distanceMatrix.length;
    }

    public Individual calculate() {

        Individual best;

        Individual[] population = GeneticOperators.initialize(numberOfIndividuals, size);
        GeneticOperators.evaluate(population, distanceMatrix);

        best = population[0];

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
