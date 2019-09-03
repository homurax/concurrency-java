package com.homurax.chapter02.matrix.util;

import java.util.Random;

public class MatrixGenerator {

    public static double[][] generate(int row, int column) {
        double[][] result = new double[row][column];
        Random random = new Random();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                result[i][j] = random.nextDouble() * 10;
            }
        }
        return result;
    }
}
