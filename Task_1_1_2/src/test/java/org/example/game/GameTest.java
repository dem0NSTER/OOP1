package org.example.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

class GameTest {
    @Test
    void playsOneRoundReportsResultAndStops() {
        Game game = new Game(new Scanner("0 0"));
        PrintStream original = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (PrintStream capture = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capture);
            game.start();
        } finally {
            System.setOut(original);
        }

        String text = output.toString(StandardCharsets.UTF_8);
        Matcher score = Pattern.compile("Игрок: (\\d+)\\RДилер: (\\d+)").matcher(text);
        assertTrue(score.find());
        assertEquals(1, text.split("Общий счёт:", -1).length - 1);
        assertTrue(text.contains("Сыграть ещё?"));

        if (text.contains("Вы победили!")) {
            assertEquals("1", score.group(1));
            assertEquals("0", score.group(2));
        } else if (text.contains("Дилер победил!")) {
            assertEquals("0", score.group(1));
            assertEquals("1", score.group(2));
        } else {
            assertTrue(text.contains("Ничья!"));
            assertEquals("0", score.group(1));
            assertEquals("0", score.group(2));
        }
    }
}
