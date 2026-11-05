package org.example;

import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A hash table using separate chaining. Null keys are not supported.
 */
public class HashTable<K, V> {
    private static final int DEFAULT_CAPACITY = 16;

    private Node<K, V>[] buckets;
    private int size;

    public HashTable() {
        @SuppressWarnings("unchecked")
        Node<K, V>[] initialBuckets = (Node<K, V>[]) new Node<?, ?>[DEFAULT_CAPACITY];
        buckets = initialBuckets;
        size = 0;
    }

    public void put(K key, V value) {
        Objects.requireNonNull(key, "Key cannot be null");

        int index = getBucketIndex(key);

        Node<K, V> current = buckets[index];

        while (current != null) {
            if (current.key.equals(key)) {
                throw new IllegalArgumentException(
                        "Key already exists: " + key
                );
            }

            current = current.next;
        }

        buckets[index] = new Node<>(key, value, buckets[index]);
        size++;
    }

    public V get(K key) {
        Objects.requireNonNull(key, "Key cannot be null");

        int index = getBucketIndex(key);
        Node<K, V> current = buckets[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }

            current = current.next;
        }

        throw new NoSuchElementException("Key not found: " + key);
    }

    public void remove(K key) {
        Objects.requireNonNull(key, "Key cannot be null");

        int index = getBucketIndex(key);

        Node<K, V> current = buckets[index];
        Node<K, V> previous = null;

        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {
                    buckets[index] = current.next;
                } else {
                    previous.next = current.next;
                }

                size--;
                return;
            }

            previous = current;
            current = current.next;
        }

        throw new NoSuchElementException("Key not found: " + key);
    }

    private int getBucketIndex(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    private static class Node<K, V> {
        private final K key;
        private V value;
        private Node<K, V> next;

        private Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

}
