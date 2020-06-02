package com.homurax.matrixmultiplier.main;

import com.homurax.matrixmultiplier.parallel.individual.ParallelIndividualMultiplier;
import com.homurax.matrixmultiplier.util.MatrixGenerator;

import java.time.Duration;
import java.time.LocalDateTime;

public class ParallelIndividualMain {

    public static void main(String[] args) {

        double[][] matrix1 = MatrixGenerator.generate(2000, 2000);
        double[][] matrix2 = MatrixGenerator.generate(2000, 2000);
        double[][] resultParallel = new double[matrix1.length][matrix2[0].length];

        LocalDateTime start = LocalDateTime.now();
        ParallelIndividualMultiplier.multiply(matrix1, matrix2, resultParallel);
        LocalDateTime end = LocalDateTime.now();
        Duration duration = Duration.between(start, end);
        System.out.println("Parallel Individual: " + duration);
    }

}
