package org.example;

/**
 * Provides methods for sorting arrays.
 */
public class Sorter {

    /**
     * Sorts the specified array using heap sort.
     *
     * @param array array to sort
     */
    public static void heapSort(int[] array) {
        Heap heap = new Heap(array);

        for (int i = array.length - 1; i >= 0; i--) {
            array[i] = heap.extractMax();
        }
    }

}
