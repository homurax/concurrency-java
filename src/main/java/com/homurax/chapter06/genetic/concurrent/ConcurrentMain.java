package com.homurax.chapter06.genetic.concurrent;

import com.homurax.chapter06.genetic.common.DataLoader;
import com.homurax.chapter06.genetic.common.Individual;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;

public class ConcurrentMain {

    public static void main(String[] args) throws IOException {

        int generations = 10000;
        int individuals = 1000;

        for (String name : new String[]{"lau15_dist", "kn57_dist"}) {

            int[][] distanceMatrix = DataLoader.load(Paths.get("src\\main\\java\\com\\homurax\\chapter06\\genetic\\data", name + ".txt"));
            ConcurrentGeneticAlgorithm concurrentGeneticAlgorithm = new ConcurrentGeneticAlgorithm(distanceMatrix, generations, individuals);

            long start = System.currentTimeMillis();
            Individual result = concurrentGeneticAlgorithm.calculate();
            long end = System.currentTimeMillis();

            System.out.println("=======================================");
            System.out.println("Example:" + name);
            System.out.println("Generations: " + generations);
            System.out.println("Population: " + individuals);
            System.out.println("Execution Time: " + (end - start));
            System.out.println("Best Individual: " + result);
            System.out.println("Total Distance: " + result.getValue());
            System.out.println("=======================================");
        }
    }

}
