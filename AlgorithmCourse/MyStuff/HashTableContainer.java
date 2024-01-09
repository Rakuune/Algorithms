import java.util.function.Predicate;

import oy.interact.tira.util.Pair;
import oy.interact.tira.util.TIRAKeyedContainer;

public class HashTableContainer<K extends Comparable<K>, V> implements TIRAKeyedContainer<K, V> {
    private static final int INITIAL_CAPACITY = 20;
    private Entry<K, V>[] table;
    private int size;

    @SuppressWarnings("unchecked")
    public HashTableContainer() {
        table = (Entry<K, V>[]) new Entry[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public void add(K key, V value) {
        if (key == null)
            throw new IllegalArgumentException("Key cannot be null");
        int index = getIndexForKey(key);
        Entry<K, V> newEntry = new Entry<>(key, value);

        if (table[index] == null) {
            table[index] = newEntry;
            size++;
        } else {
            Entry<K, V> current = table[index];
            while (current != null) {
                if (current.key.equals(key)) {
                    current.value = value;
                    return;
                }
                if (current.next == null) {
                    current.next = newEntry;
                    size++;
                    return;
                }
                current = current.next;
            }
        }
    }

    @Override
    public V get(K key) {
        if (key == null)
            return null;
        int index = getIndexForKey(key);

        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    private int getIndexForKey(K key) {
        int hashCode = key.hashCode();
        return Math.abs(hashCode % table.length);
    }

    private static class Entry<K, V> {
        K key;
        V value;
        Entry<K, V> next;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    @Override
    public V remove(K key) throws IllegalArgumentException {
        if (key == null)
            throw new IllegalArgumentException("Key cannot be null");
        int index = getIndexForKey(key);
        Entry<K, V> current = table[index];
        Entry<K, V> previous = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return current.value;
            }
            previous = current;
            current = current.next;
        }

        return null;
    }

    @Override
    public V find(Predicate<V> searcher) {
        for (Entry<K, V> entry : table) {
            while (entry != null) {
                if (searcher.test(entry.value)) {
                    return entry.value;
                }
                entry = entry.next;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int capacity() {
        return table.length;
    }

    @Override
    public void ensureCapacity(int capacity) throws OutOfMemoryError, IllegalArgumentException {
        throw new UnsupportedOperationException("Dynamic capacity adjustment not supported");
    }

    @Override
    public void clear() {
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    @Override
    public Pair<K, V>[] toArray() {
        @SuppressWarnings("unchecked")
        Pair<K, V>[] array = new Pair[size];
        int index = 0;
        for (Entry<K, V> entry : table) {
            while (entry != null) {
                array[index++] = new Pair<>(entry.key, entry.value);
                entry = entry.next;
            }
        }
        return array;
    }
}
