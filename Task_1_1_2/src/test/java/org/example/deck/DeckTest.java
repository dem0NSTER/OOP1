package org.example.deck;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.example.deck.cards.Card;
import org.example.deck.cards.Rank;
import org.example.deck.cards.Suit;
import org.junit.jupiter.api.Test;

class DeckTest {
    @Test
    void drawsEachCardExactlyOnceAndRejectsEmptyDeck() {
        Deck deck = new Deck();
        deck.shuffle();
        Set<Card> drawn = new HashSet<>();

        for (int i = 0; i < Rank.values().length * Suit.values().length; i++) {
            assertTrue(drawn.add(deck.draw()), "дублирование карты " + i);
        }

        assertEquals(52, drawn.size());
        assertThrows(IllegalStateException.class, deck::draw);
    }

    @Test
    void cardSymbolsAndValues() {
        assertEquals("♠Q♠", new Card(Rank.QUEEN, Suit.SPADES).toString());
        assertEquals(11, Rank.ACE.getValue());
        assertEquals(10, Rank.JACK.getValue());
        assertEquals("♦", Suit.DIAMONDS.getSymbol());
    }
}
