package com.homurax.chapter02.matrix.parallel;

import com.homurax.chapter02.matrix.parallel.group.ParallelGroupMultiplier;
import com.homurax.chapter02.matrix.parallel.individual.ParallelIndividualMultiplier;
import com.homurax.chapter02.matrix.parallel.row.ParallelRowMultiplier;
import com.homurax.chapter02.matrix.util.MatrixGenerator;

public class ParallelMain {

    public static void main(String[] args) {

        double[][] matrix1 = MatrixGenerator.generate(2000, 2000);
        double[][] matrix2 = MatrixGenerator.generate(2000, 2000);
        double[][] result = new double[matrix1.length][matrix2[0].length];
        individual(matrix1, matrix2, result);
        row(matrix1, matrix2, result);
        group(matrix1, matrix2, result);
    }


    private static void individual(double[][] matrix1, double[][] matrix2, double[][] result) {

        long start = System.currentTimeMillis();
        ParallelIndividualMultiplier.multiply(matrix1, matrix2, result);
        long end = System.currentTimeMillis();
        System.out.printf("Individual: %d%n", end - start);
    }

    private static void row(double[][] matrix1, double[][] matrix2, double[][] result) {

        long start = System.currentTimeMillis();
        ParallelRowMultiplier.multiply(matrix1, matrix2, result);
        long end = System.currentTimeMillis();
        System.out.printf("Row: %d%n", end - start);
    }

    private static void group(double[][] matrix1, double[][] matrix2, double[][] result) {

        long start = System.currentTimeMillis();
        ParallelGroupMultiplier.multiply(matrix1, matrix2, result);
        long end = System.currentTimeMillis();
        System.out.printf("Group: %d%n", end - start);
    }
}
