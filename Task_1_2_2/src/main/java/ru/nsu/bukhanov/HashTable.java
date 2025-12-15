package ru.nsu.bukhanov;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Обобщённая хеш-таблица HashTable<K, V>.
 * Коллизии решаются цепочками (связные списки внутри бакетов.
 * Поддерживает:
 *  - put / get / remove / update / containsKey
 *  - итерацию по парам (K, V) с ConcurrentModificationException
 *  - equals / hashCode / toString
 */
public class HashTable<K, V> implements Iterable<Map.Entry<K, V>> {

    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    // package-private, чтобы к ним мог обратиться HashTableIterator
    private HashTableNode<K, V>[] table;
    private int size;
    private final float loadFactor;
    private int modCount;

    public HashTable() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    public HashTable(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    @SuppressWarnings("unchecked")
    public HashTable(int initialCapacity, float loadFactor) {
        if (initialCapacity <= 0) {
            throw new IllegalArgumentException("initialCapacity must be positive");
        }
        if (!(loadFactor > 0.0f)) {
            throw new IllegalArgumentException("loadFactor must be positive");
        }

        int capacity = 1;
        while (capacity < initialCapacity) {
            capacity <<= 1;   // округляем до степени двойки
        }

        this.table = (HashTableNode<K, V>[]) new HashTableNode[capacity];
        this.loadFactor = loadFactor;
        this.size = 0;
        this.modCount = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        Arrays.fill(table, null);
        size = 0;
        modCount++;
    }

    public boolean containsKey(K key) {
        return getNode(key) != null;
    }

    public V get(K key) {
        HashTableNode<K, V> node = getNode(key);
        if (node == null) {
            throw new NoSuchElementException("Key not found: " + key);
        }
        return node.getValue();
    }

    public V getOrDefault(K key, V defaultValue) {
        HashTableNode<K, V> node = getNode(key);
        return node == null ? defaultValue : node.getValue();
    }

    public V put(K key, V value) {
        Objects.requireNonNull(key, "key cannot be null");

        int hash = hash(key);
        int index = indexFor(hash, table.length);

        HashTableNode<K, V> node = table[index];
        while (node != null) {
            if (node.getHash() == hash && Objects.equals(node.getKey(), key)) {
                V old = node.getValue();
                node.setValue(value);
                return old;
            }
            node = node.getNext();
        }

        HashTableNode<K, V> newNode = new HashTableNode<>(hash, key, value, table[index]);
        table[index] = newNode;
        size++;
        modCount++;

        if (size > table.length * loadFactor) {
            resize();
        }

        return null;
    }

    public void update(K key, V newValue) {
        HashTableNode<K, V> node = getNode(key);
        if (node == null) {
            throw new NoSuchElementException("Key not found: " + key);
        }
        node.setValue(newValue);
        // модификации структуры нет, значит и modCount не трогаем
    }

    public V remove(K key) {
        Objects.requireNonNull(key, "key cannot be null");
        if (size == 0) {
            return null;
        }

        int hash = hash(key);
        int index = indexFor(hash, table.length);

        HashTableNode<K, V> node = table[index];
        HashTableNode<K, V> prev = null;

        while (node != null) {
            if (node.getHash() == hash && Objects.equals(node.getKey(), key)) {
                if (prev == null) {
                    table[index] = node.getNext();
                } else {
                    prev.setNext(node.getNext());
                }
                size--;
                modCount++;
                return node.getValue();
            }
            prev = node;
            node = node.getNext();
        }

        return null;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new HashTableIterator<>(this);
    }

    // ======= equals / hashCode / toString =======

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HashTable)) {
            return false;
        }
        HashTable<K, V> other = (HashTable<K, V>) o;

        if (this.size != other.size) {
            return false;
        }

        for (Map.Entry<K, V> e : this) {
            K key = e.getKey();
            V value = e.getValue();

            if (!other.containsKey(key)) {
                return false;
            }
            V otherValue = other.getOrDefault(key, null);
            if (!Objects.equals(value, otherValue)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int h = 0;
        for (Map.Entry<K, V> e : this) {
            h += e.hashCode();
        }
        return h;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        Iterator<Map.Entry<K, V>> it = iterator();
        while (it.hasNext()) {
            Map.Entry<K, V> e = it.next();
            sb.append(e.getKey()).append('=').append(e.getValue());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append('}');
        return sb.toString();
    }
    // ======= Вспомогательные методы =======

    private HashTableNode<K, V> getNode(K key) {
        Objects.requireNonNull(key, "key cannot be null");
        if (size == 0) {
            return null;
        }
        int hash = hash(key);
        int index = indexFor(hash, table.length);
        HashTableNode<K, V> node = table[index];
        while (node != null) {
            if (node.getHash() == hash && Objects.equals(node.getKey(), key)) {
                return node;
            }
            node = node.getNext();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        int newCapacity = table.length << 1;
        HashTableNode<K, V>[] newTable = (HashTableNode<K, V>[]) new HashTableNode[newCapacity];

        for (HashTableNode<K, V> head : table) {
            HashTableNode<K, V> node = head;
            while (node != null) {
                HashTableNode<K, V> next = node.getNext();
                int index = indexFor(node.getHash(), newCapacity);
                node.setNext(newTable[index]);
                newTable[index] = node;
                node = next;
            }
        }

        table = newTable;
        modCount++;
    }

    private int hash(Object key) {
        int h = key.hashCode();
        h ^= (h >>> 16);
        return h;
    }

    private int indexFor(int hash, int length) {
        return hash & (length - 1);
    }

    int getLength() {
        return table.length;
    }


    HashTableNode<K, V> getBucket(int bucketIndex) {
        return table[bucketIndex];
    }

    int getModCount() {
        return modCount;
    }
}
