package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PrimeLogicTest {
    @Test
    void testEdgeCases() {
        NonPrimeFinder f = new SequentialFinder();
        assertTrue(f.isNotPrime(0));   // n < 2
        assertTrue(f.isNotPrime(1));   // n < 2
        assertFalse(f.isNotPrime(2));  // Простое
        assertTrue(f.isNotPrime(4));   // Составное
    }
}