package ru.nsu.bukhanov;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Итератор по HashTable<K, V>.
 */
class HashTableIterator<K, V> implements Iterator<Map.Entry<K, V>> {

    private final HashTable<K, V> table;
    private int bucketIndex;
    private HashTableNode<K, V> current;
    private HashTableNode<K, V> next;
    private int expectedModCount;
    private boolean canRemove;

    HashTableIterator(HashTable<K, V> table) {
        this.table = table;
        this.expectedModCount = table.getModCount();
        this.bucketIndex = -1;
        this.current = null;
        this.next = null;
        this.canRemove = false;
        advance();
    }

    private void advance() {
        if (next != null && next.getNext() != null) {
            next = next.getNext();
            return;
        }

        next = null;
        bucketIndex++;
        while (bucketIndex < table.getLength() && table.getBucket(bucketIndex) == null) {
            bucketIndex++;
        }
        if (bucketIndex < table.getLength()) {
            next = table.getBucket(bucketIndex);
        }
    }

    private void checkForComodification() {
        if (expectedModCount != table.getModCount()) {
            throw new ConcurrentModificationException();
        }
    }

    @Override
    public boolean hasNext() {
        return next != null;
    }

    @Override
    public Map.Entry<K, V> next() {
        checkForComodification();
        if (next == null) {
            throw new NoSuchElementException();
        }
        current = next;
        advance();
        canRemove = true;
        return current;
    }

    @Override
    public void remove() {
        checkForComodification();
        if (!canRemove) {
            throw new IllegalStateException("next() has not been called or element already removed");
        }
        table.remove(current.getKey());
        expectedModCount = table.getModCount();
        canRemove = false;
        current = null;
    }
}
