package org.example;

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
