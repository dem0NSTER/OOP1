package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class HeapSortTest {

    @Test
    void sortArray() {
        int[] arr = {5, 3, 4, 2, 6};

        HeapSort.sort(arr);

        assertArrayEquals(
                new int[]{2, 3, 4, 5, 6},
                arr
        );
    }

}