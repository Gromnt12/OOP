package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class KmpStreamSearcherTest {

    @Test
    void prefixFunctionAllSame() {
        int[] pattern = "aaaaa".codePoints().toArray();
        int[] pi = KmpStreamSearcher.prefixFunction(pattern);
        assertArrayEquals(new int[]{0, 1, 2, 3, 4}, pi);
    }

    @Test
    void prefixFunctionWithFallbacks() {
        int[] pattern = "abacab".codePoints().toArray();
        int[] pi = KmpStreamSearcher.prefixFunction(pattern);
        assertArrayEquals(new int[]{0, 0, 1, 0, 1, 2}, pi);
    }
}
