package com.homurax.chapter02.matrix.serial;

import com.homurax.chapter02.matrix.util.MatrixGenerator;

import java.util.Date;

public class SerialMain {

    public static void main(String[] args) {

        double[][] matrix1 = MatrixGenerator.generate(2000, 2000);
        double[][] matrix2 = MatrixGenerator.generate(2000, 2000);
        double[][] result = new double[matrix1.length][matrix2[0].length];
        long start = System.currentTimeMillis();
        SerialMultiplier.multiply(matrix1, matrix2, result);
        long end = System.currentTimeMillis();
        System.out.printf("Serial: %d%n", end - start);
    }

}
