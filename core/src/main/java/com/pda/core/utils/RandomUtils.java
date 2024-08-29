package com.pda.core.utils;

import java.util.Random;

public class RandomUtils {

    private static final Random random = new Random();

    public static double createRandomDouble(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    public static long createRandomInt(int min, int max) {
        return (int) (Math.random() * max) + min;
    }
}
