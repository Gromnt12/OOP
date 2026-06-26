package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FindersTest {
    @Test
    void testAllImplementations() {
        long[] withNonPrime = {6, 8, 7, 13, 5, 9, 4}; // Из условия [cite: 82]
        long[] onlyPrimes = {20319251, 6997901, 6997927}; // Из условия [cite: 83]

        NonPrimeFinder[] finders = {
                new SequentialFinder(),
                new ThreadFinder(4),
                new StreamFinder()
        };

        for (NonPrimeFinder f : finders) {
            assertTrue(f.hasNonPrime(withNonPrime));
            assertFalse(f.hasNonPrime(onlyPrimes));
        }
    }
}