package org.example.deck;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.example.deck.cards.Card;
import org.example.deck.cards.Rank;
import org.example.deck.cards.Suit;

/** Стандартная колода игральных карт. */
public class Deck {
    private final List<Card> cards;

    /** Перемешивает карты в колоде. */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Удаляет и возвращает следующую карту из колоды.
     *
     * @return следующая карта
     * @throws IllegalStateException если колода пуста
     */
    public Card draw() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("колода пуста");
        }

        return cards.removeLast();
    }

    /** Создаёт полную колоду со всеми достоинствами и мастями. */
    public Deck() {
        this.cards = new ArrayList<>();

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(rank, suit));
            }
        }
    }
}
