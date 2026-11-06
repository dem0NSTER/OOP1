package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HashTableTest {
    @Test
    void iteratesCollisionChainsAndOtherBucketsExactlyOnce() {
        HashTable<Integer, String> table = new HashTable<>();
        List<Map.Entry<Integer, String>> expected = List.of(
                Map.entry(1, "first"),
                Map.entry(17, "second"),
                Map.entry(33, "third"),
                Map.entry(15, "other bucket")
        );
        for (Map.Entry<Integer, String> entry : expected) {
            table.put(entry.getKey(), entry.getValue());
        }

        List<Map.Entry<Integer, String>> actual = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : table) {
            actual.add(entry);
        }

        assertEquals(expected.size(), actual.size());
        assertTrue(actual.containsAll(expected));
    }

    @Test
    void putInvalidatesIteratorDuringIteration() {
        HashTable<Integer, String> table = new HashTable<>();
        table.put(1, "first");
        table.put(17, "second");
        Iterator<Map.Entry<Integer, String>> iterator = table.iterator();
        iterator.next();

        table.put(33, "third");

        assertThrows(ConcurrentModificationException.class, iterator::hasNext);
        assertThrows(ConcurrentModificationException.class, iterator::next);
    }
}
