package com.homurax.matrixmultiplier.main;

import com.homurax.matrixmultiplier.parallel.group.ParallelGroupMultiplier;
import com.homurax.matrixmultiplier.util.MatrixGenerator;

import java.time.Duration;
import java.time.LocalDateTime;

public class ParallelGroupMain {

    public static void main(String[] args) {

        double[][] matrix1 = MatrixGenerator.generate(2000, 2000);
        double[][] matrix2 = MatrixGenerator.generate(2000, 2000);
        double[][] resultParallelGroup = new double[matrix1.length][matrix2[0].length];

        LocalDateTime start = LocalDateTime.now();
        ParallelGroupMultiplier.multiply(matrix1, matrix2, resultParallelGroup);
        LocalDateTime end = LocalDateTime.now();
        Duration duration = Duration.between(start, end);
        System.out.println("Parallel Group: " + duration);

    }

}
