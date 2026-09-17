package org.example;

import java.util.Scanner;

import org.example.game.Game;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Game game = new Game(scanner);
        game.start();
    }

}
