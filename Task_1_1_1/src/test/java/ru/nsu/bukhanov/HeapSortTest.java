package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HeapSortTest {

    @Test
    public void testBasicSort() {
        int[] input = {5, 4, 3, 2, 1};
        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, HeapSort.heapsort(input));
    }

    @Test
    public void testAlreadySorted() {
        int[] input = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, HeapSort.heapsort(input));
    }

    @Test
    public void testReverseSorted() {
        int[] input = {5, 2, 3, 4, 1};
        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, HeapSort.heapsort(input));
    }

    @Test
    public void testDuplicateElements() {
        int[] input = {4, 2, 4, 1, 2, 3};
        int[] expected = {1, 2, 2, 3, 4, 4};
        assertArrayEquals(expected, HeapSort.heapsort(input));
    }

    @Test
    public void testSingleElement() {
        int[] input = {42};
        int[] expected = {42};
        assertArrayEquals(expected, HeapSort.heapsort(input));
    }

    @Test
    public void testEmptyArray() {
        int[] input = {};
        int[] expected = {};
        assertArrayEquals(expected, HeapSort.heapsort(input));
    }

    @Test
    public void testNullArray() {
        int[] input = null;
        assertDoesNotThrow(() -> HeapSort.heapsort(input));
    }

    @Test
    public void testLargeArray() {
        int[] input = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        int[] expected = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertArrayEquals(expected, HeapSort.heapsort(input));
    }

    @Test
    public void testNegativeNumbers() {
        int[] input = {-3, -1, -2, 0, 2, 1};
        int[] expected = {-3, -2, -1, 0, 1, 2};
        assertArrayEquals(expected, HeapSort.heapsort(input));
    }

    @Test
    public void testPerformance() {
        // Тест производительности для проверки сложности O(n log n)
        int size = 10000;
        int[] largeArray = new int[size];
        for (int i = 0; i < size; i++) {
            largeArray[i] = size - i - 1;
        }
        long startTime = System.nanoTime();
        HeapSort.heapsort(largeArray);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Время сортировки " + size + " элементов: " + duration + " наносекунд");
        for (int i = 0; i < size - 1; i++) {
            assertTrue(largeArray[i] <= largeArray[i + 1]);
        }
    }
}