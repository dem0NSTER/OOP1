package org.example;

public class KmpSubstringSearcher {

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

}
