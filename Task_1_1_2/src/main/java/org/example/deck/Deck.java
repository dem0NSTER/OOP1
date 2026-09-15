package org.example.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import org.example.deck.cards.Card;
import org.example.deck.cards.Rank;
import org.example.deck.cards.Suit;

public class Deck {
    private final List<Card> cards;

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card draw() {
        if  (cards.isEmpty()) {
            throw new IllegalStateException("колода пуста");
        }

        return cards.removeLast();
    }

    public Deck() {
        this.cards = new ArrayList<>();

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
        
    }

}
