package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class HashTableIteratorTest {

    @Test
    void testIterationOverAllElements() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("one", 1);
        table.put("two", 2);
        table.put("three", 3);

        int sum = 0;
        for (Map.Entry<String, Integer> e : table) {
            sum += e.getValue();
        }
        assertEquals(6, sum);
    }

    @Test
    void testConcurrentModificationThrows() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("one", 1);
        table.put("two", 2);
        table.put("three", 3);

        Iterator<Map.Entry<String, Integer>> it = table.iterator();
        assertTrue(it.hasNext());
        it.next();                // получили первый элемент
        table.put("four", 4);     // модифицировали таблицу после создания итератора

        assertThrows(ConcurrentModificationException.class, it::next);
    }

    @Test
    void testIteratorRemoveRemovesElement() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("one", 1);
        table.put("two", 2);

        Iterator<Map.Entry<String, Integer>> it = table.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> e = it.next();
            if (e.getKey().equals("one")) {
                it.remove();
            }
        }

        assertFalse(table.containsKey("one"));
        assertEquals(1, table.size());
        assertTrue(table.containsKey("two"));
    }

    @Test
    void testIteratorNextOnEmptyThrows() {
        HashTable<String, Integer> table = new HashTable<>();
        Iterator<Map.Entry<String, Integer>> it = table.iterator();

        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, it::next);
    }
}
