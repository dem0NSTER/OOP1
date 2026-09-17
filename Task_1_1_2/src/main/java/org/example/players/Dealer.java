package org.example.players;

import org.example.players.helpers.Participant;

public class Dealer extends Participant {

    public boolean shouldTakeCard() {
        return getScore() < 17;
    }
   
}
