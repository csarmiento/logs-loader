package com.camilo.quickquestions;

import java.math.BigInteger;
import java.util.Arrays;

public class QuickQuestion2 {
    public static int[] product(int[] input) {
        BigInteger total = BigInteger.ONE;
        BigInteger totalWithZeroes = BigInteger.ONE;

        int zeroes = 0;
        int[] result = new int[input.length];

        for (int anInput : input) {
            zeroes += (anInput == 0) ? 1 : 0;

            if (anInput != 0) {
                totalWithZeroes = totalWithZeroes.multiply(BigInteger.valueOf(anInput));
            }
            total = total.multiply(BigInteger.valueOf(anInput));
        }
        if (zeroes > 1) {
            // If there are more than 1 zero, the answer always will be an array of zeroes
            Arrays.fill(result, 0);
        } else {
            for (int i = 0; i < input.length; i++) {
                if (input[i] == 0) {
                    result[i] = totalWithZeroes.intValue();
                } else {
                    result[i] = total.divide(BigInteger.valueOf(input[i])).intValue();
                }
            }
        }

        return result;
    }

}