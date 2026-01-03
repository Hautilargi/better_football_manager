package com.hautilargi.footman.core.util;

import java.util.Map;
import java.util.Random;

public class WeightedRandom {

    private static final Random random = new Random();

    public static <T> T getRandomByWeight(Map<T, Double> weightedResults) {
        if (weightedResults.isEmpty()) {
            throw new IllegalArgumentException("Map must not be empty");
        }

        double totalWeight = 0.0;
        for (double weight : weightedResults.values()) {
            if (weight < 0) {
                throw new IllegalArgumentException("Weights must be non-negative");
            }
            totalWeight += weight;
        }

        if (totalWeight <= 0) {
            throw new IllegalArgumentException("Total weight must be greater than zero");
        }

        double r = random.nextDouble() * totalWeight;
        double cumulative = 0.0;

        for (Map.Entry<T, Double> entry : weightedResults.entrySet()) {
            cumulative += entry.getValue();
            if (r < cumulative) {
                return entry.getKey();
            }
        }

        // Fallback (should never happen)
        return weightedResults.keySet().iterator().next();
    }
}
