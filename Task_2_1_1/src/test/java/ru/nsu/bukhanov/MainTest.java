package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @Test
    void testMainInvocation() {
        // Берем крошечный массив и всего 1 замер (N=1)
        long[] tinyData = {2, 3, 4};
        int testN = 1;

        // Просто проверяем, что метод запускается и не вылетает с ошибкой
        assertDoesNotThrow(() -> Main.runAllMeasurements(tinyData, testN));
    }
}