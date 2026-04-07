package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HashTableNodeTest {

    @Test
    void testGettersAndSetValue() {
        HashTableNode<String, Integer> node =
                new HashTableNode<>("key".hashCode(), "key", 1, null);

        assertEquals("key", node.getKey());
        assertEquals(1, node.getValue());

        Integer old = node.setValue(10);
        assertEquals(1, old);
        assertEquals(10, node.getValue());
    }

    @Test
    void testEqualsAndHashCode() {
        HashTableNode<String, Integer> n1 =
                new HashTableNode<>("key".hashCode(), "key", 1, null);
        HashTableNode<String, Integer> n2 =
                new HashTableNode<>("key".hashCode(), "key", 1, null);

        assertEquals(n1, n2);
        assertEquals(n1.hashCode(), n2.hashCode());

        HashTableNode<String, Integer> n3 =
                new HashTableNode<>("another".hashCode(), "another", 1, null);
        assertNotEquals(n1, n3);
    }

    @Test
    void testEqualsWithMapEntry() {
        HashTableNode<String, Integer> node =
                new HashTableNode<>("key".hashCode(), "key", 1, null);

        Map.Entry<String, Integer> entry = new Map.Entry<>() {
            @Override
            public String getKey() {
                return "key";
            }

            @Override
            public Integer getValue() {
                return 1;
            }

            @Override
            public Integer setValue(Integer value) {
                return null;
            }
        };

        assertEquals(node, entry);
    }

    @Test
    void testToStringFormat() {
        HashTableNode<String, Integer> node =
                new HashTableNode<>("key".hashCode(), "key", 1, null);

        assertEquals("key=1", node.toString());
    }
}
