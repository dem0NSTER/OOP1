package org.example.players;

import org.example.deck.cards.Card;

public class Participant {
    private final Hand hand;

    public Participant() {
        this.hand = new Hand();
    }

    public void takeCard(Card card) {
        hand.addCard(card);
    }

    public int getScore() {
        return hand.getScore();
    }

    public boolean isBust() {
        return hand.isBust();
    }

    public boolean hasBlackjack() {
        return hand.hasBlackjack();
    }
}
