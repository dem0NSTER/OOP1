package org.example.game;

import org.example.deck.Deck;
import org.example.players.Participant;

public class Game {
    private final Deck deck;
    private final Participant player;
    private final Participant dealer;

    public Game() {
        deck = new Deck();
        player = new Participant();
        dealer = new Participant();
    }
}
