package ru.nsu.bukhanov;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

/**
 * Фасад для поиска подстроки в файле.
 * Индексы считаются в Unicode code points (а не в байтах), чтобы корректно работать с UTF-8.
 */
public final class SubstringFinder {

    public static List<Long> find(String filename, String needle) throws IOException {
        return find(Path.of(filename), needle);
    }

    /**
     * Находит все позиции начала вхождений needle в UTF-8 файле file.
     */
    public static List<Long> find(Path file, String needle) throws IOException {
        Objects.requireNonNull(file, "file must not be null");
        Objects.requireNonNull(needle, "needle must not be null");

        if (needle.isEmpty()) {
            throw new IllegalArgumentException("needle must not be empty");
        }

        int[] pattern = needle.codePoints().toArray();
        return KmpStreamSearcher.search(file, pattern);
    }

    private SubstringFinder() {
        // static methods only
    }
}
