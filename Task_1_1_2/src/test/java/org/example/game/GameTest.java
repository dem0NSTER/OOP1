package org.example.game;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import org.example.game.round.RoundResult;
import org.junit.jupiter.api.Test;

class GameTest {
    private static void invokePrivate(Game game, String methodName, RoundResult result) throws Exception {
        Method method = Game.class.getDeclaredMethod(methodName, RoundResult.class);
        method.setAccessible(true);
        method.invoke(game, result);
    }

    private static Object invokePrivate(Game game, String methodName) throws Exception {
        Method method = Game.class.getDeclaredMethod(methodName);
        method.setAccessible(true);
        return method.invoke(game);
    }

    @Test
    void updatesAndPrintsScoreForAllResults() throws Exception {
        Game game = new Game(new Scanner("0"));
        PrintStream original = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (PrintStream capture = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capture);
            invokePrivate(game, "updateScore", RoundResult.PLAYER_WIN);
            invokePrivate(game, "updateScore", RoundResult.DEALER_WIN);
            invokePrivate(game, "updateScore", RoundResult.DRAW);
            invokePrivate(game, "printResult", RoundResult.PLAYER_WIN);
            invokePrivate(game, "printResult", RoundResult.DEALER_WIN);
            invokePrivate(game, "printResult", RoundResult.DRAW);
            invokePrivate(game, "printScore");
        } finally {
            System.setOut(original);
        }

        String text = output.toString(StandardCharsets.UTF_8);
        assertTrue(text.contains("Вы победили!"));
        assertTrue(text.contains("Дилер победил!"));
        assertTrue(text.contains("Ничья!"));
        assertTrue(text.contains("Игрок: 1"));
        assertTrue(text.contains("Дилер: 1"));
    }

    @Test
    void startsRoundAndStopsWhenRequested() {
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
        assertTrue(text.contains("Общий счёт:"));
        assertTrue(text.contains("Сыграть ещё?"));
    }

    @Test
    void continuesOnlyForOne() throws Exception {
        Game game = new Game(new Scanner("1 0"));
        assertTrue((boolean) invokePrivate(game, "shouldContinue"));
        assertFalse((boolean) invokePrivate(game, "shouldContinue"));
    }
}
