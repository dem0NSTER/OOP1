package org.example.game.round;

import java.util.Scanner;

import org.example.deck.Deck;
import org.example.players.Dealer;
import org.example.players.Player;

public class Round {

    private final Scanner scanner;

    private final Deck deck;

    private final Player player;

    private final Dealer dealer;

    public Round(Scanner scanner) {
        this.scanner = scanner;
        this.deck = new Deck();
        this.player = new Player();
        this.dealer = new Dealer();

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
        while (dealer.shouldTakeCard()) {
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
