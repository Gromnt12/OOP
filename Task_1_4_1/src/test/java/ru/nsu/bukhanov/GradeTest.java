package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GradeTest {

    @Test
    void helpersWork() {
        assertTrue(Grade.SATISFACTORY.isSatisfactoryOrWorse());
        assertTrue(Grade.UNSATISFACTORY.isSatisfactoryOrWorse());
        assertFalse(Grade.GOOD.isSatisfactoryOrWorse());

        assertTrue(Grade.EXCELLENT.isExcellent());
        assertFalse(Grade.GOOD.isExcellent());

        assertEquals(5, Grade.EXCELLENT.value());
        assertEquals(2, Grade.UNSATISFACTORY.value());
    }
}
