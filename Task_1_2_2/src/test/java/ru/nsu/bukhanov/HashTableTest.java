package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {

    @Test
    void testPutAndGet() {
        HashTable<String, Integer> table = new HashTable<>();

        assertEquals(0, table.size());
        assertTrue(table.isEmpty());

        assertNull(table.put("one", 1));
        assertEquals(1, table.size());
        assertFalse(table.isEmpty());
        assertEquals(1, table.get("one"));

        Integer old = table.put("one", 10);
        assertEquals(1, old);
        assertEquals(10, table.get("one"));
    }

    @Test
    void testGetMissingThrows() {
        HashTable<String, Integer> table = new HashTable<>();
        assertThrows(NoSuchElementException.class, () -> table.get("missing"));
    }

    @Test
    void testUpdate() {
        HashTable<String, String> table = new HashTable<>();
        table.put("key", "value");
        table.update("key", "new");

        assertEquals("new", table.get("key"));
        assertThrows(NoSuchElementException.class, () -> table.update("other", "x"));
    }

    @Test
    void testRemove() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("one", 1);
        table.put("two", 2);

        assertEquals(2, table.size());
        assertEquals(1, table.remove("one"));
        assertEquals(1, table.size());
        assertFalse(table.containsKey("one"));

        assertNull(table.remove("one")); // повторное удаление
    }

    @Test
    void testContainsKey() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("a", 1);

        assertTrue(table.containsKey("a"));
        assertFalse(table.containsKey("b"));
    }

    @Test
    void testResizeKeepsElements() {
        HashTable<Integer, Integer> table = new HashTable<>(2);
        int count = 100;

        for (int i = 0; i < count; i++) {
            table.put(i, i * i);
        }

        assertEquals(count, table.size());

        for (int i = 0; i < count; i++) {
            assertEquals(i * i, table.get(i));
        }
    }

    @Test
    void testEqualsAndHashCode() {
        HashTable<String, Integer> t1 = new HashTable<>();
        HashTable<String, Integer> t2 = new HashTable<>();

        t1.put("one", 1);
        t1.put("two", 2);
        t2.put("two", 2);
        t2.put("one", 1);

        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());

        t2.put("three", 3);
        assertNotEquals(t1, t2);
    }

    @Test
    void testToString() {
        HashTable<String, Integer> table = new HashTable<>();
        assertEquals("{}", table.toString());

        table.put("one", 1);
        String s = table.toString();

        assertTrue(s.startsWith("{"));
        assertTrue(s.endsWith("}"));
        assertTrue(s.contains("one=1"));
    }

    @Test
    void testClear() {
        HashTable<Integer, String> table = new HashTable<>();
        table.put(1, "one");
        table.put(2, "two");

        table.clear();

        assertEquals(0, table.size());
        assertTrue(table.isEmpty());
    }
}
