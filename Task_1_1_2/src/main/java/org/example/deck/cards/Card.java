package org.example.deck.cards;

/** Игральная карта, заданная достоинством и мастью. */
public record Card(Rank rank, Suit suit) {

    @Override
    public String toString() {
        return suit.getSymbol() + rank.getSymbol() + suit.getSymbol();
    }

}
