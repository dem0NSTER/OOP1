package org.example;

public class Sorter {

    public static void heapSort(int[] array) {
        Heap heap = new Heap(array);

        for (int i = array.length - 1; i >= 0; i--) {
            array[i] = heap.extractMax();
        }
    }

}
