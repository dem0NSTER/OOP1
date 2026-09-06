package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SorterTest {

    @Test
    void sortsUnsortedArray() {
        int[] array = {5, 4, 3, 2, 1};
        Sorter.heapSort(array);

        assertArrayEquals(
                new int[]{1, 2, 3, 4, 5},
                array
        );
    }

    @Test
    void sortsEmptyArray() {
        int[] array = {};
        Sorter.heapSort(array);

        assertArrayEquals(
                new int[]{},
                array
        );
    }

    @Test
    void sortsSortedArray() {
        int[] array = {1, 2, 3, 4, 5};
        Sorter.heapSort(array);

        assertArrayEquals(
                new int[]{1, 2, 3, 4, 5},
                array
        );
    }

    @Test
    void sortsArrayWithDuplicates() {
        int[] array = {6, 1, 7, 8, 8, 1};
        Sorter.heapSort(array);

        assertArrayEquals(
                new int[]{1, 1, 6, 7, 8, 8},
                array
        );
    }

}