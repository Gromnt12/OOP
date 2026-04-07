package ru.nsu.bukhanov;

public class SequentialFinder implements NonPrimeFinder {
    @Override
    public boolean hasNonPrime(long[] numbers) {
        for (long num : numbers) {
            if (isNotPrime(num)) return true;
        }
        return false;
    }
}