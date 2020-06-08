package com.homurax.geneticAlgorithm.common;

import lombok.Data;

@Data
public class Individual implements Comparable<Individual> {

    private Integer[] chromosomes;
    private int value;

    public Individual(int size) {
        this.chromosomes = new Integer[size];
    }

    public Individual(Individual other) {
        this.chromosomes = other.getChromosomes().clone();
    }

    @Override
    public int compareTo(Individual o) {
        return Integer.compare(this.getValue(), o.getValue());
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (Integer number : chromosomes) {
            ret.append(number).append(";");
        }
        return ret.toString();
    }

}
