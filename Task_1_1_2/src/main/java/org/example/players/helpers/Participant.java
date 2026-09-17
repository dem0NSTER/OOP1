package org.example.players.helpers;

import org.example.deck.cards.Card;

public class Participant {
    protected final Hand hand;

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

    public Card getCard(int index) {
        return hand.getCard(index);
    }

    @Override
    public String toString() {
        return hand.toString();
    }
}
