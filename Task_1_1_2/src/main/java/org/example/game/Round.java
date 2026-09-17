package org.example.game;

import java.util.Scanner;

import org.example.deck.Deck;
import org.example.players.Participant;

public class Round {

    private final Scanner scanner;

    private final Deck deck;

    private final Participant player;

    private final Participant dealer;

    public Round(Scanner scanner) {
        this.scanner = scanner;
        this.deck = new Deck();
        this.player = new Participant();
        this.dealer = new Participant();

        deck.shuffle();
    }

    public void dealInitialCards() {
        player.takeCard(deck.draw());
        dealer.takeCard(deck.draw());

        player.takeCard(deck.draw());
        dealer.takeCard(deck.draw());
    }

    public RoundResult playRound() {
        dealInitialCards();

        // блекджек
        if (player.hasBlackjack() && dealer.hasBlackjack()) {
            return RoundResult.DRAW;
        }
        if (player.hasBlackjack()) {
            return RoundResult.PLAYER_WIN;
        }
        if (dealer.hasBlackjack()) {
            return RoundResult.DEALER_WIN;
        }

        // ход игрока
        if (!playPlayerTurn()) {
            return RoundResult.DEALER_WIN;
        }

        // Ход дилера
        playDealerTurn();

        return determineWinner();
    }

    private RoundResult determineWinner() {
        if (player.isBust()) {
            return RoundResult.DEALER_WIN;
        }

        if (dealer.isBust()) {
            return RoundResult.PLAYER_WIN;
        }

        if (player.getScore() > dealer.getScore()) {
            return RoundResult.PLAYER_WIN;
        }

        if (dealer.getScore() > player.getScore()) {
            return RoundResult.DEALER_WIN;
        }

        return RoundResult.DRAW;
    }

    private void playDealerTurn() {
        while (dealer.getScore() < 17) {
            dealer.takeCard(deck.draw());
        }
    }

    private boolean playPlayerTurn() {

        while (true) {
            System.out.println("1 — взять карту");
            System.out.println("0 — остановиться");

            int choice = scanner.nextInt();

            if (choice == 0) {
                return true;
            }

            if (choice == 1) {
                player.takeCard(deck.draw());
                if (player.isBust()) {
                    return false;
                }
            }
        }
    }

}
