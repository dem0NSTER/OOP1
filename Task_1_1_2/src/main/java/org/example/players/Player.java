package org.example.players;

import org.example.players.helpers.Participant;

public class Player extends Participant {

    @Override
    public String toString() {
        return hand.toString();
    }
}
