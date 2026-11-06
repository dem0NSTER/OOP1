package org.example;

import java.util.AbstractMap;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * A hash table using separate chaining. Null keys are not supported.
 */
public class HashTable<K, V> implements Iterable<Map.Entry<K, V>> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private Node<K, V>[] buckets;
    private int size;
    private int modCount;

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
        modCount++;

        if (size > buckets.length * LOAD_FACTOR) {
            resize();
        }
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
                modCount++;
                return;
            }

            previous = current;
            current = current.next;
        }

        throw new NoSuchElementException("Key not found: " + key);
    }

    public void update(K key, V value) {
        Objects.requireNonNull(key, "Key cannot be null");

        int index = getBucketIndex(key);

        Node<K, V> current = buckets[index];

        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                modCount++;
                return;
            }

            current = current.next;
        }

        throw new NoSuchElementException("Key not found: " + key);
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new HashTableIterator();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HashTable<?, ?> other)) {
            return false;
        }
        if (size != other.size) {
            return false;
        }

        for (Map.Entry<K, V> entry : this) {
            Node<?, ?> node = other.findNode(entry.getKey());
            if (node == null || !Objects.equals(entry.getValue(), node.value)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (Map.Entry<K, V> entry : this) {
            hash += Objects.hashCode(entry.getKey()) ^ Objects.hashCode(entry.getValue());
        }
        return hash;
    }

    @Override
    public String toString() {
        StringJoiner result = new StringJoiner(", ", "{", "}");
        for (Map.Entry<K, V> entry : this) {
            result.add(entry.getKey() + "=" + entry.getValue());
        }
        return result.toString();
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K, V>[] oldBuckets = buckets;

        Node<K, V>[] newBuckets = (Node<K, V>[]) new Node<?, ?>[oldBuckets.length * 2];

        for (Node<K, V> head : oldBuckets) {
            Node<K, V> current = head;

            while (current != null) {
                Node<K, V> next = current.next;

                int newIndex = Math.floorMod(
                        current.key.hashCode(),
                        newBuckets.length
                );

                current.next = newBuckets[newIndex];
                newBuckets[newIndex] = current;

                current = next;
            }
        }

        buckets = newBuckets;
    }

    private int getBucketIndex(K key) {
        return Math.floorMod(key.hashCode(), buckets.length);
    }

    private Node<K, V> findNode(Object key) {
        int index = Math.floorMod(key.hashCode(), buckets.length);
        Node<K, V> current = buckets[index];

        while (current != null) {
            if (current.key.equals(key)) {
                return current;
            }
            current = current.next;
        }

        return null;
    }

    private class HashTableIterator implements Iterator<Map.Entry<K, V>> {
        private final int expectedModCount = modCount;
        private int bucketIndex;
        private Node<K, V> nextNode;

        private HashTableIterator() {
            advanceToNextBucket();
        }

        @Override
        public boolean hasNext() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }

            return nextNode != null;
        }

        @Override
        public Map.Entry<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Node<K, V> current = nextNode;
            nextNode = current.next;
            advanceToNextBucket();

            return new AbstractMap.SimpleImmutableEntry<>(current.key, current.value);
        }

        private void advanceToNextBucket() {
            while (nextNode == null && bucketIndex < buckets.length) {
                nextNode = buckets[bucketIndex++];
            }
        }
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
