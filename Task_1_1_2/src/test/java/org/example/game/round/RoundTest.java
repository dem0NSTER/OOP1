package org.example.game.round;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Scanner;

import org.example.deck.Deck;
import org.example.deck.cards.Card;
import org.example.deck.cards.Rank;
import org.example.deck.cards.Suit;
import org.junit.jupiter.api.Test;

class RoundTest {
    private static Round round(String choices, Card... drawOrder) throws ReflectiveOperationException {
        Round round = new Round(new Scanner(choices));

        Field deckField = Round.class.getDeclaredField("deck");
        deckField.setAccessible(true);
        Deck deck = (Deck) deckField.get(round);

        Field cardsField = Deck.class.getDeclaredField("cards");
        cardsField.setAccessible(true);
        @SuppressWarnings("unchecked")
        List<Card> cards = (List<Card>) cardsField.get(deck);

        cards.clear();

        for (int i = drawOrder.length - 1; i >= 0; i--) {
            cards.add(drawOrder[i]);
        }

        return round;
    }

    @Test
    void bothBlackjacksDraw() throws Exception {
        RoundResult expected = RoundResult.DRAW;

        RoundResult actual = round(
                "",
                new Card(Rank.ACE, Suit.HEARTS),
                new Card(Rank.ACE, Suit.SPADES),
                new Card(Rank.KING, Suit.HEARTS),
                new Card(Rank.QUEEN, Suit.SPADES)
        ).playRound();

        assertEquals(expected, actual);
    }

    @Test
    void playerBlackjackWins() throws Exception {
        RoundResult expected = RoundResult.PLAYER_WIN;

        RoundResult actual = round(
                "",
                new Card(Rank.ACE, Suit.HEARTS),
                new Card(Rank.NINE, Suit.SPADES),
                new Card(Rank.KING, Suit.HEARTS),
                new Card(Rank.SEVEN, Suit.SPADES)
        ).playRound();

        assertEquals(expected, actual);
    }

    @Test
    void dealerBlackjackWins() throws Exception {
        RoundResult expected = RoundResult.DEALER_WIN;

        RoundResult actual = round(
                "",
                new Card(Rank.NINE, Suit.HEARTS),
                new Card(Rank.ACE, Suit.SPADES),
                new Card(Rank.SEVEN, Suit.HEARTS),
                new Card(Rank.KING, Suit.SPADES)
        ).playRound();

        assertEquals(expected, actual);
    }

    @Test
    void playerBustLosesImmediately() throws Exception {
        RoundResult expected = RoundResult.DEALER_WIN;

        RoundResult actual = round(
                "1",
                new Card(Rank.TEN, Suit.HEARTS),
                new Card(Rank.FIVE, Suit.SPADES),
                new Card(Rank.NINE, Suit.HEARTS),
                new Card(Rank.SIX, Suit.SPADES),
                new Card(Rank.FIVE, Suit.HEARTS)
        ).playRound();

        assertEquals(expected, actual);
    }

    @Test
    void dealerBustMakesPlayerWin() throws Exception {
        RoundResult expected = RoundResult.PLAYER_WIN;

        RoundResult actual = round(
                "0",
                new Card(Rank.TEN, Suit.HEARTS),
                new Card(Rank.TEN, Suit.SPADES),
                new Card(Rank.EIGHT, Suit.HEARTS),
                new Card(Rank.SIX, Suit.SPADES),
                new Card(Rank.KING, Suit.CLUBS)
        ).playRound();

        assertEquals(expected, actual);
    }

    @Test
    void comparesScoresAndCanDraw() throws Exception {
        RoundResult expected = RoundResult.PLAYER_WIN;
        RoundResult actual = round(
                "0",
                new Card(Rank.TEN, Suit.HEARTS),
                new Card(Rank.TEN, Suit.SPADES),
                new Card(Rank.NINE, Suit.HEARTS),
                new Card(Rank.EIGHT, Suit.SPADES)
        ).playRound();
        assertEquals(expected, actual);

        expected = RoundResult.DEALER_WIN;
        actual = round(
                "0",
                new Card(Rank.TEN, Suit.HEARTS),
                new Card(Rank.TEN, Suit.SPADES),
                new Card(Rank.SEVEN, Suit.HEARTS),
                new Card(Rank.EIGHT, Suit.SPADES)
        ).playRound();
        assertEquals(expected, actual);

        expected = RoundResult.DRAW;
        actual = round(
                "0",
                new Card(Rank.TEN, Suit.HEARTS),
                new Card(Rank.NINE, Suit.SPADES),
                new Card(Rank.EIGHT, Suit.HEARTS),
                new Card(Rank.NINE, Suit.CLUBS)
        ).playRound();
        assertEquals(expected, actual);
    }

    @Test
    void dealerDrawsUntilSeventeen() throws Exception {
        RoundResult expected = RoundResult.PLAYER_WIN;

        RoundResult actual = round(
                "0",
                new Card(Rank.TEN, Suit.HEARTS),
                new Card(Rank.EIGHT, Suit.SPADES),
                new Card(Rank.EIGHT, Suit.HEARTS),
                new Card(Rank.SEVEN, Suit.SPADES),
                new Card(Rank.TWO, Suit.CLUBS)
        ).playRound();

        assertEquals(expected, actual);
    }

}
