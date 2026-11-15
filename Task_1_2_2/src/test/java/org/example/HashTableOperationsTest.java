package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class HashTableOperationsTest {
    @Test
    void equalityAndHashCodeDoNotDependOnCollisionChainOrder() {
        HashTable<CollisionKey, String> first = new HashTable<>();
        HashTable<CollisionKey, String> second = new HashTable<>();
        for (int i = 0; i < 5; i++) {
            first.put(new CollisionKey(i), "value-" + i);
        }
        for (int i = 4; i >= 0; i--) {
            second.put(new CollisionKey(i), "value-" + i);
        }

        assertEquals(first, second);
        assertEquals(second, first);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void multipleEntryStringContainsEveryPairWithoutRequiringOrder() {
        HashTable<String, Integer> table = new HashTable<>();
        table.put("one", 1);
        table.put("two", 2);
        table.put("three", 3);

        String result = table.toString();
        assertTrue(result.startsWith("{"));
        assertTrue(result.endsWith("}"));
        String[] pairs = result.substring(1, result.length() - 1).split(", ");
        Arrays.sort(pairs);
        assertEquals(Arrays.asList("one=1", "three=3", "two=2"), Arrays.asList(pairs));
    }

    @Test
    void missingKeysThrowWithoutChangingTable() {
        HashTable<CollisionKey, String> table = new HashTable<>();
        table.put(new CollisionKey(1), "value");

        assertThrows(NoSuchElementException.class, () -> table.get(new CollisionKey(2)));
        assertThrows(NoSuchElementException.class, () -> table.remove(new CollisionKey(2)));
        assertThrows(NoSuchElementException.class,
                () -> table.update(new CollisionKey(2), "missing"));

        assertEquals("value", table.get(new CollisionKey(1)));
        assertEquals("value", table.iterator().next().getValue());
    }

    @Test
    void resizePreservesAllCollidingKeysAndValues() {
        HashTable<CollisionKey, String> table = new HashTable<>();
        for (int i = 0; i < 20; i++) {
            table.put(new CollisionKey(i), "value-" + i);
        }

        for (int i = 0; i < 20; i++) {
            assertEquals("value-" + i, table.get(new CollisionKey(i)));
        }

        table.update(new CollisionKey(10), "updated");
        table.remove(new CollisionKey(11));

        assertEquals("updated", table.get(new CollisionKey(10)));
        assertThrows(NoSuchElementException.class, () -> table.get(new CollisionKey(11)));
        assertEquals("value-12", table.get(new CollisionKey(12)));
    }

    private static class CollisionKey {
        private final int id;

        private CollisionKey(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof CollisionKey other && id == other.id;
        }

        @Override
        public int hashCode() {
            return 7;
        }
    }
}
