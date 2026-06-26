package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ThreadSpecificTest {
    @Test
    void testInterruption() {
        ThreadFinder finder = new ThreadFinder(2);
        Thread.currentThread().interrupt();
        assertFalse(finder.hasNonPrime(new long[]{2, 3}));
        assertTrue(Thread.interrupted()); // Сброс флага
    }
}