package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] arr = {5, 3, 4, 2, 6};

        Sorter.heapSort(arr);

        System.out.println(Arrays.toString(arr));
    }
}