package ru.nsu.bukhanov;

import java.util.Map;
import java.util.Objects;

/**
 * Узел хеш-таблицы: хранит пару (key, value) и ссылку на следующий узел.
 */
class HashTableNode<K, V> implements Map.Entry<K, V> {
    private final int hash;
    private final K key;
    private V value;
    private HashTableNode<K, V> next;

    HashTableNode(int hash, K key, V value, HashTableNode<K, V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V newValue) {
        V old = value;
        value = newValue;
        return old;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(key) ^ Objects.hashCode(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Map.Entry<?, ?> e)) {
            return false;
        }
        return Objects.equals(key, e.getKey())
                && Objects.equals(value, e.getValue());
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }

    int getHash() {
        return hash;
    }

    HashTableNode<K, V> getNext() {
        return next;
    }

    void setNext(HashTableNode<K, V> node) {
        next = node;
    }
}
