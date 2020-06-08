package com.homurax.geneticAlgorithm.concurrent;

import com.homurax.geneticAlgorithm.common.DataLoader;
import com.homurax.geneticAlgorithm.common.Individual;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;

public class ConcurrentMain {

    public static void main(String[] args) throws IOException {

        int generations = 10000;
        int individuals = 1000;

        for (String name : new String[]{"lau15_dist", "kn57_dist"}) {
            int[][] distanceMatrix = DataLoader.load(Paths.get("data", name + ".txt"));

            ConcurrentGeneticAlgorithm concurrentGeneticAlgorithm = new ConcurrentGeneticAlgorithm(distanceMatrix, generations, individuals);
            LocalDateTime start = LocalDateTime.now();
            Individual result = concurrentGeneticAlgorithm.calculate();
            LocalDateTime end = LocalDateTime.now();
            Duration duration = Duration.between(start, end);

            System.out.println("=======================================");
            System.out.println("Example:" + name);
            System.out.println("Generations: " + generations);
            System.out.println("Population: " + individuals);
            System.out.println("Execution Time: " + duration);
            System.out.println("Best Individual: " + result);
            System.out.println("Total Distance: " + result.getValue());
            System.out.println("=======================================");
        }

    }

}
