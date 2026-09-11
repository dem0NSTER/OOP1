package org.example;

import java.util.Arrays;

public class Heap {

    private int[] data;

    private int size;

    public Heap(int[] arr) {
        data = Arrays.copyOf(arr, arr.length);
        size = arr.length;

        for (int i = size / 2 - 1; i >= 0; i--) {
            heapify(i);
        }
    }

    public int extractMax() {
        if (size <= 0) {
            throw new IllegalStateException("куча пуста");
        }

        final int max = data[0];

        data[0] = data[size - 1];
        size--;

        if (size > 0) {
            heapify(0);
        }
        return max;
    }

    private void heapify(int index) {
        int largest = index;

        int left = index * 2 + 1;
        int right = index * 2 + 2;

        if (left < size && data[left] > data[largest]) {
            largest = left;
        }
        if (right < size && data[right] > data[largest]) {
            largest = right;
        }

        if (largest != index) {
            int tmp = data[index];
            data[index] = data[largest];
            data[largest] = tmp;

            heapify(largest);
        }
    }

}
