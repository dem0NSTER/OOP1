package org.example.players.helpers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.deck.cards.Card;
import org.example.deck.cards.Rank;
import org.example.deck.cards.Suit;
import org.example.players.Dealer;
import org.example.players.Player;
import org.junit.jupiter.api.Test;

class HandTest {

    @Test
    void scoresAcesAsOneWhenElevenWouldBust() {
        Hand hand = new Hand();
        hand.addCard(new Card(Rank.ACE, Suit.HEARTS));
        hand.addCard(new Card(Rank.ACE, Suit.HEARTS));
        hand.addCard(new Card(Rank.NINE, Suit.HEARTS));

        int expectedScore = 21;
        int actualScore = hand.getScore();

        assertEquals(expectedScore, actualScore);

        boolean expectedBust = false;
        boolean actualBust = hand.isBust();

        assertEquals(expectedBust, actualBust);

        hand.addCard(new Card(Rank.TWO, Suit.HEARTS));
        expectedScore = 13;
        actualScore = hand.getScore();
        actualBust = hand.isBust();

        assertEquals(expectedScore, actualScore);
        assertEquals(expectedBust, actualBust);
    }

    @Test
    void reportsBustAndBlackjackOnlyForTwoCards() {
        Hand hand = new Hand();
        hand.addCard(new Card(Rank.ACE, Suit.HEARTS));
        hand.addCard(new Card(Rank.KING, Suit.HEARTS));

        boolean expectedBlackjack = true;
        boolean actualBlackjack = hand.hasBlackjack();

        assertEquals(expectedBlackjack, actualBlackjack);

        Card expectedCard = new Card(Rank.ACE, Suit.HEARTS);
        Card actualCard = hand.getCard(0);

        assertEquals(expectedCard, actualCard);

        String expectedHand = "[♥A♥, ♥K♥]";
        String actualHand = hand.toString();

        assertEquals(expectedHand, actualHand);

        hand.addCard(new Card(Rank.TWO, Suit.HEARTS));

        expectedBlackjack = false;
        actualBlackjack = hand.hasBlackjack();

        assertEquals(expectedBlackjack, actualBlackjack);

        boolean expectedBust = false;
        boolean actualBust = hand.isBust();

        assertEquals(expectedBust, actualBust);

        Hand threeCardsWithTwentyOne = new Hand();
        threeCardsWithTwentyOne.addCard(new Card(Rank.SEVEN, Suit.HEARTS));
        threeCardsWithTwentyOne.addCard(new Card(Rank.SEVEN, Suit.HEARTS));
        threeCardsWithTwentyOne.addCard(new Card(Rank.SEVEN, Suit.HEARTS));

        int expectedScore = 21;
        int actualScore = threeCardsWithTwentyOne.getScore();

        assertEquals(expectedScore, actualScore);

        actualBlackjack = threeCardsWithTwentyOne.hasBlackjack();

        assertEquals(expectedBlackjack, actualBlackjack);

        threeCardsWithTwentyOne.addCard(new Card(Rank.TWO, Suit.HEARTS));

        expectedBust = true;
        actualBust = threeCardsWithTwentyOne.isBust();

        assertEquals(expectedBust, actualBust);
    }

    @Test
    void participantsExposeHandAndDealerStopsAtSeventeen() {
        Player player = new Player();

        player.takeCard(new Card(Rank.TEN, Suit.HEARTS));
        player.takeCard(new Card(Rank.SIX, Suit.HEARTS));

        int expectedScore = 16;
        int actualScore = player.getScore();

        assertEquals(expectedScore, actualScore);

        boolean expectedBust = false;
        boolean actualBust = player.isBust();

        assertEquals(expectedBust, actualBust);

        boolean expectedBlackjack = false;
        boolean actualBlackjack = player.hasBlackjack();

        assertEquals(expectedBlackjack, actualBlackjack);

        Card expectedCard = new Card(Rank.TEN, Suit.HEARTS);
        Card actualCard = player.getCard(0);

        assertEquals(expectedCard, actualCard);

        String expectedHand = "[♥10♥, ♥6♥]";
        String actualHand = player.toString();

        assertEquals(expectedHand, actualHand);

        Dealer dealer = new Dealer();

        dealer.takeCard(new Card(Rank.TEN, Suit.HEARTS));
        dealer.takeCard(new Card(Rank.SIX, Suit.HEARTS));

        boolean expectedShouldTakeCard = true;
        boolean actualShouldTakeCard = dealer.shouldTakeCard();

        assertEquals(expectedShouldTakeCard, actualShouldTakeCard);

        String expectedHiddenHand = "[♥10♥, ???]";
        String actualHiddenHand = dealer.getHiddenHandView();

        assertEquals(expectedHiddenHand, actualHiddenHand);

        dealer.takeCard(new Card(Rank.ACE, Suit.HEARTS));

        expectedScore = 17;
        actualScore = dealer.getScore();

        assertEquals(expectedScore, actualScore);

        expectedShouldTakeCard = false;
        actualShouldTakeCard = dealer.shouldTakeCard();

        assertEquals(expectedShouldTakeCard, actualShouldTakeCard);

        expectedHand = "[♥10♥, ♥6♥, ♥A♥]";
        actualHand = dealer.toString();

        assertEquals(expectedHand, actualHand);
    }
}
