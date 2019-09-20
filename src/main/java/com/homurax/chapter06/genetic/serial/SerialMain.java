package com.homurax.chapter06.genetic.serial;

import com.homurax.chapter06.genetic.common.DataLoader;
import com.homurax.chapter06.genetic.common.Individual;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;

public class SerialMain {

    public static void main(String[] args) throws IOException {

        int generations = 10000;
        int individuals = 1000;

        for (String name : new String[]{"lau15_dist", "kn57_dist"}) {

            int[][] distanceMatrix = DataLoader.load(Paths.get("src\\main\\java\\com\\homurax\\chapter06\\genetic\\data", name + ".txt"));
            SerialGeneticAlgorithm serialGeneticAlgorithm = new SerialGeneticAlgorithm(distanceMatrix, generations, individuals);

            long start = System.currentTimeMillis();
            Individual result = serialGeneticAlgorithm.calculate();
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
