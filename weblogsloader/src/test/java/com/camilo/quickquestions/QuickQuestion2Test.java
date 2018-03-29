package com.camilo.quickquestions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QuickQuestion2Test {

    @Test
    public void test1() {
        int[] input = new int[]{3, 1, 6, 4};
        int[] expectedOutput = new int[]{24, 72, 12, 18};

        int[] product = QuickQuestion2.product(input);

        assertEquals(expectedOutput.length, product.length);
        for (int i = 0; i < expectedOutput.length; i++) {
            assertEquals(expectedOutput[i], product[i]);
        }
    }

    @Test
    public void test2() {
        int[] input = new int[]{0, 1, 6, 4};
        int[] expectedOutput = new int[]{24, 0, 0, 0};

        int[] product = QuickQuestion2.product(input);

        assertEquals(expectedOutput.length, product.length);
        for (int i = 0; i < expectedOutput.length; i++) {
            assertEquals(expectedOutput[i], product[i]);
        }

    }

    @Test
    public void test3() {
        int[] input = new int[]{0, 1, 6, 4, 0};
        int[] expectedOutput = new int[]{0, 0, 0, 0, 0};

        int[] product = QuickQuestion2.product(input);

        assertEquals(expectedOutput.length, product.length);
        for (int i = 0; i < expectedOutput.length; i++) {
            assertEquals(expectedOutput[i], product[i]);
        }
    }
}
