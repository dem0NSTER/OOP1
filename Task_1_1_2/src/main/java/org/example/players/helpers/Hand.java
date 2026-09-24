package org.example.players.helpers;

import java.util.ArrayList;
import java.util.List;
import org.example.deck.cards.Card;
import org.example.deck.cards.Rank;

/** Рука в блэкджеке и правила подсчёта её очков. */
public class Hand {

    private final List<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Вычисляет наилучшее количество очков для карт в руке.
     *
     * @return количество очков в руке
     */
    public int getScore() {
        int score = 0;
        int aces = 0;

        for (Card card : cards) {
            score += card.rank().getValue();
            if (card.rank() == Rank.ACE) {
                aces++;
            }
        }

        while (score > 21 && aces > 0) {
            score -= 10;
            aces--;
        }

        return score;

    }

    public boolean isBust() {
        int score = getScore();
        return score > 21;
    }

    public boolean hasBlackjack() {
        return cards.size() == 2 && getScore() == 21;
    }

    public Card getCard(int index) {
        return cards.get(index);
    }

    @Override
    public String toString() {
        return cards.toString();
    }

}
