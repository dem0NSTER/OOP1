package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class KmpSubstringSearcher implements SubstringSearcher {

    private static int[] prefixFunction(int[] pattern) {
        int[] prefix = new int[pattern.length];

        for (int i = 1; i < pattern.length; i++) {
            int j = prefix[i - 1];

            while (j > 0 && pattern[i] != pattern[j]) {
                j = prefix[j - 1];
            }

            if (pattern[i] == pattern[j]) {
                j++;
            }

            prefix[i] = j;
        }

        return prefix;
    }

    @Override
    public List<Long> find(Path file, String pattern) throws IOException {
        if (pattern.isEmpty()) {
            throw new IllegalArgumentException("Pattern cannot be empty");
        }

        int[] patternCodePoints = pattern.codePoints().toArray();
        int[] prefix = prefixFunction(patternCodePoints);

        List<Long> positions = new ArrayList<>();

        int j = 0;
        long position = 0;

        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            int codePoint;

            while ((codePoint = readCodePoint(reader)) != -1) {

                while (j > 0 && codePoint != patternCodePoints[j]) {
                    j = prefix[j - 1];
                }

                if (codePoint == patternCodePoints[j]) {
                    j++;
                }

                if (j == patternCodePoints.length) {
                    positions.add(position - patternCodePoints.length + 1);
                    j = prefix[j - 1];
                }

                position++;
            }
        }

        return positions;
    }

    private static int readCodePoint(BufferedReader reader) throws IOException {

        int first = reader.read();

        if (first == -1) {
            return -1;
        }

        char ch = (char) first;

        if (Character.isHighSurrogate(ch)) {
            int second = reader.read();

            if (second == -1 || !Character.isLowSurrogate((char) second)) {
                throw new IOException("Invalid surrogate pair");
            }

            return Character.toCodePoint(ch, (char) second);
        }

        if (Character.isLowSurrogate(ch)) {
            throw new IOException("Unexpected low surrogate");
        }

        return first;
    }

}
