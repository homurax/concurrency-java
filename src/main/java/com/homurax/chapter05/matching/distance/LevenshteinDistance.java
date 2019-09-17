package com.homurax.chapter05.matching.distance;

public class LevenshteinDistance {

    public static int calculate(String string1, String string2) {

        int[][] dp = new int[string1.length() + 1][string2.length() + 1];

        for (int i = 1; i <= string1.length(); i++) {
            dp[i][0] = i;
        }

        for (int j = 1; j <= string2.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= string1.length(); i++) {
            for (int j = 1; j <= string2.length(); j++) {
                if (string1.charAt(i - 1) == string2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = min(dp[i - 1][j], dp[i][j - 1], dp[i - 1][j - 1]) + 1;
                }
            }
        }

        return dp[string1.length()][string2.length()];
    }

    private static int min(int i, int j, int k) {
        return Math.min(i, Math.min(j, k));
    }
}
