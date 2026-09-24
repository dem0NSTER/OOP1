package org.example;

import java.util.Scanner;
import org.example.game.Game;

/** Точка входа в приложение. */
public class Main {

    /**
     * Запускает игру в блэкджек с использованием стандартного ввода.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Game game = new Game(scanner);
        game.start();
    }

}
