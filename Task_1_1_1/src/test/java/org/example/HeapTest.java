package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;


class HeapTest {

    @Test
    void extractMax() {
        int[] array = {4, 3, 5, 2, 1};
        var heap = new Heap(array);

        assertEquals(5, heap.extractMax());
    }

    @Test
    void extractMaxNoElements() {
        Heap heap = new Heap(new int[]{});

        assertThrows(
                IllegalStateException.class,
                heap::extractMax
        );
    }

    @Test
    void extractSeveralElements() {
        int[] array = {3, 5, 2, 1};
        Heap heap = new Heap(array);

        assertEquals(5, heap.extractMax());
        assertEquals(3, heap.extractMax());
        assertEquals(2, heap.extractMax());
        assertEquals(1, heap.extractMax());
    }
}