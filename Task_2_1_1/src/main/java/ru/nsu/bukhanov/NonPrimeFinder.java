package ru.nsu.bukhanov;

public interface NonPrimeFinder {
    boolean hasNonPrime(long[] numbers);
    default boolean isNotPrime(long n) {
        if (n < 2) return true;
        for (long i = 2; i * i <= n; i++) {
            if (n % i == 0) return true;
        }
        return false;
    }
}