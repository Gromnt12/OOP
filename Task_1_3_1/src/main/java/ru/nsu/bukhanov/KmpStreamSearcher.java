package ru.nsu.bukhanov;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Потоковый поиск KMP по Unicode code points.
 */
final class KmpStreamSearcher {


    static List<Long> search(Path file, int[] pattern) throws IOException {
        int[] pi = prefixFunction(pattern);
        List<Long> result = new ArrayList<>();

        long index = 0;
        int j = 0;

        try (Utf8CodePointReader reader = Utf8CodePointReader.open(file)) {
            int cp;
            while ((cp = reader.nextCodePoint()) != -1) {

                while (j > 0 && cp != pattern[j]) {
                    j = pi[j - 1];
                }
                if (cp == pattern[j]) {
                    j++;
                }
                if (j == pattern.length) {
                    result.add(index - pattern.length + 1);
                    j = pi[j - 1]; // перекрывающиеся вхождения
                }

                index++;
            }
        }

        return result;
    }

    /**
     * Префикс-функция (KMP).
     */
    static int[] prefixFunction(int[] pattern) {
        int[] pi = new int[pattern.length];
        int j = 0;
        for (int i = 1; i < pattern.length; i++) {
            while (j > 0 && pattern[i] != pattern[j]) {
                j = pi[j - 1];
            }
            if (pattern[i] == pattern[j]) {
                j++;
            }
            pi[i] = j;
        }
        return pi;
    }
    private KmpStreamSearcher() {
    }
}
