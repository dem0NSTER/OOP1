package org.example.game;

import java.util.Scanner;

import org.example.game.round.Round;
import org.example.game.round.RoundResult;

public class Game {

    private final Scanner scanner;

    private int playerWins;

    private int dealerWins;

    public Game(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        while (true) {
            System.out.println("**********************************");
            Round round = new Round(scanner);
            RoundResult result = round.playRound();

            updateScore(result);
            printResult(result);
            printScore();

            System.out.println("**********************************");
            if (!shouldContinue()) {
                break;
            }
        }
    }

    private void updateScore(RoundResult roundResult) {
        switch (roundResult) {
            case PLAYER_WIN -> playerWins++;
            case DEALER_WIN -> dealerWins++;
            case DRAW -> {
            }
        }
    }

    private void printResult(RoundResult roundResult) {
        switch (roundResult) {
            case PLAYER_WIN -> System.out.println("Вы победили!");
            case DEALER_WIN -> System.out.println("Дилер победил!");
            case DRAW -> System.out.println("Ничья!");
        }
    }

    private void printScore() {
        System.out.println();
        System.out.println("Общий счёт:");
        System.out.println("Игрок: " + playerWins);
        System.out.println("Дилер: " + dealerWins);
    }

    private boolean shouldContinue() {
        System.out.println();
        System.out.println("Сыграть ещё?");
        System.out.println("1 — да");
        System.out.println("0 — нет");

        int choice = scanner.nextInt();

        return choice == 1;
    }
}
