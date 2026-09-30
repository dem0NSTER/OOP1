package org.example.game.round;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class RoundTest {
    private record PlayedRound(RoundResult result, String output) {
    }

    private static PlayedRound play(String choices) {
        Round round = new Round(new Scanner(choices));
        PrintStream original = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        RoundResult result;

        try (PrintStream capture = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capture);
            result = round.playRound();
        } finally {
            System.setOut(original);
        }

        return new PlayedRound(result, output.toString(StandardCharsets.UTF_8));
    }

    @Test
    void standingCompletesRoundWithoutDrawingForPlayer() {
        PlayedRound played = play("0");

        assertNotNull(played.result());
        assertTrue(played.output().contains("Ваши карты:"));
        assertTrue(played.output().contains("Ваши очки:"));
        assertTrue(played.output().contains("Карты дилера:"));
        assertFalse(played.output().contains("Вы взяли:"));
        assertTrue(played.output().contains("Дилер открывает карты:")
                || played.output().contains("Blackjack!"));
    }

    @Test
    void takingCardsEndsInBustUnlessRoundEndsWithBlackjack() {
        PlayedRound played = play("1 ".repeat(52));

        assertNotNull(played.result());
        if (played.output().contains("Вы взяли:")) {
            assertEquals(RoundResult.DEALER_WIN, played.result());
            assertTrue(played.output().contains("Перебор!"));
        } else {
            assertTrue(played.output().contains("Blackjack!"));
        }
    }
}
