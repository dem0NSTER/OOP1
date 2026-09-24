package org.example.players;

import org.example.players.helpers.Participant;

/** Игрок, участвующий в партии в блэкджек. */
public class Player extends Participant {

    @Override
    public String toString() {
        return hand.toString();
    }
}
