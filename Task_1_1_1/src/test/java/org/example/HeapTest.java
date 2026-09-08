package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


class HeapTest {

    @Test
    void extractMax() {
        int[] array = {5, 4, 3, 2, 1};
        var heap = new Heap(array);

        assertEquals(5, heap.extractMax());
    }

    @Test
    void extractMaxNoElements() {
        int[] array = new int[]{};
        var heap = new Heap(array);

        assertEquals(-1, heap.extractMax());
    }
}