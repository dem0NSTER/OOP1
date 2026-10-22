package org.example;

/**
 * Демонстрационный запуск приложения.
 */
public class Main {
    /**
     * Выводит приветствие и числа от одного до пяти.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {

        System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {

            System.out.println("i = " + i);
        }
    }
}
