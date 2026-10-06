package org.example.game.round;

import java.util.Scanner;
import org.example.deck.Deck;
import org.example.deck.cards.Card;
import org.example.players.Dealer;
import org.example.players.Player;

/** Один раунд игры в блэкджек. */
public class Round {

    private final Scanner scanner;

    private final Deck deck;

    private final Player player;

    private final Dealer dealer;

    /**
     * Создаёт раунд, считывающий действия игрока из переданного сканера.
     *
     * @param scanner сканер для ввода действий игрока
     */
    public Round(Scanner scanner) {
        this.scanner = scanner;
        this.deck = new Deck();
        this.player = new Player();
        this.dealer = new Dealer();

        deck.shuffle();
    }

    /** Раздаёт по две карты игроку и дилеру. */
    public void dealInitialCards() {
        player.takeCard(deck.draw());
        dealer.takeCard(deck.draw());

        player.takeCard(deck.draw());
        dealer.takeCard(deck.draw());
    }

    /**
     * Проводит раунд до получения результата.
     *
     * @return результат раунда
     */
    public RoundResult playRound() {
        dealInitialCards();
        printInitialState();

        // блекджек
        if (player.hasBlackjack() && dealer.hasBlackjack()) {
            System.out.println("Карты дилера: " + dealer);
            System.out.println("У тебя и у дилера Blackjack!");
            return RoundResult.DRAW;
        }
        if (player.hasBlackjack()) {
            System.out.println("У тебя Blackjack!");
            System.out.println("Карты дилера: " + dealer);
            return RoundResult.PLAYER_WIN;
        }
        if (dealer.hasBlackjack()) {
            System.out.println("Карты дилера: " + dealer);
            System.out.println("У дилера Blackjack!");
            return RoundResult.DEALER_WIN;
        }

        // ход игрока
        if (!playPlayerTurn()) {
            System.out.println("Перебор! У вас " + player.getScore() + " очков.");
            return RoundResult.DEALER_WIN;
        }

        // Ход дилера
        playDealerTurn();

        return determineWinner();
    }

    private RoundResult determineWinner() {
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
        System.out.println();
        System.out.println("Дилер открывает карты: " + dealer);
        System.out.println("Очки дилера: " + dealer.getScore());

        while (dealer.shouldTakeCard()) {
            Card card = deck.draw();
            dealer.takeCard(card);

            System.out.println("Дилер взял: " + card);
            System.out.println("Карты дилера: " + dealer);
            System.out.println("Очки дилера: " + dealer.getScore());
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
                Card card = deck.draw();
                player.takeCard(card);

                System.out.println("Вы взяли: " + card);
                System.out.println("Ваши карты: " + player);
                System.out.println("Ваши очки: " + player.getScore());

                if (player.isBust()) {
                    return false;
                }
            }
        }
    }

    private void printInitialState() {
        System.out.println("Ваши карты: " + player);
        System.out.println("Ваши очки: " + player.getScore());
        System.out.println();

        System.out.println("Карты дилера: " + dealer.getHiddenHandView());
    }
}
