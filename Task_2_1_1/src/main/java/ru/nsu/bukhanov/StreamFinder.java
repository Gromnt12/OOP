package ru.nsu.bukhanov;

import java.util.Arrays;

public class StreamFinder implements NonPrimeFinder {
    @Override
    public boolean hasNonPrime(long[] numbers) {
        return Arrays.stream(numbers).parallel().anyMatch(this::isNotPrime);
    }
}