package com.homurax.chapter06.genetic.common;

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
        StringBuilder sb = new StringBuilder();
        for (Integer number : chromosomes) {
            sb.append(number).append(";");
        }
        return sb.toString();
    }

}
