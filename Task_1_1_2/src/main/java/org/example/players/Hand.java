package org.example.players;

import java.util.ArrayList;
import java.util.List;

import org.example.deck.cards.Card;
import org.example.deck.cards.Rank;

public class Hand {

    private final List<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int getScore() {
        int score = 0;
        int aces = 0;

        for (Card card : cards) {
            score += card.getRank().getValue();
            if (card.getRank() == Rank.ACE) {
                aces++;
            }
        }

        while (score > 21 && aces > 0) {
            score -= 10;
            aces--;
        }

        return score;

    }

}
