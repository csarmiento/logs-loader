package com.camilo.quickquestions;

import java.math.BigInteger;
import java.util.Arrays;

public class QuickQuestion2 {
    public static int[] product(int[] input) {
        // The case where the array has only 1 element is not defined - answer will be always 1
        if (input.length == 1) {
            return new int[]{1};
        }

        BigInteger total = BigInteger.ONE;

        int zeroes = 0;
        int[] result = new int[input.length];

        for (int anInput : input) {
            zeroes += (anInput == 0) ? 1 : 0;
            if (zeroes > 1) {
                Arrays.fill(result, 0);
                return result;
            }

            if (anInput != 0) {
                total = total.multiply(BigInteger.valueOf(anInput));
            }
        }
        for (int i = 0; i < input.length; i++) {
            if (input[i] == 0) {
                result[i] = total.intValue();
            } else if (zeroes == 1) {
                result[i] = 0;
            } else {
                result[i] = total.divide(BigInteger.valueOf(input[i])).intValue();
            }
        }

        return result;
    }

}