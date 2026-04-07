package ru.nsu.bukhanov;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class SubstringFinderTest {

    @TempDir
    Path tempDir;

    @Test
    void findsCyrillicExampleFromStatement() throws Exception {
        Path file = tempDir.resolve("input.txt");
        Files.writeString(file, "абракадабра", StandardCharsets.UTF_8);

        List<Long> res = SubstringFinder.find(file, "бра");
        assertEquals(List.of(1L, 8L), res);
    }

    @Test
    void findsOverlappingOccurrences() throws Exception {
        Path file = tempDir.resolve("a.txt");
        Files.writeString(file, "aaaa", StandardCharsets.UTF_8);

        assertEquals(List.of(0L, 1L, 2L), SubstringFinder.find(file, "aa"));
    }

    @Test
    void returnsEmptyWhenNoMatches() throws Exception {
        Path file = tempDir.resolve("x.txt");
        Files.writeString(file, "hello", StandardCharsets.UTF_8);

        assertTrue(SubstringFinder.find(file, "world").isEmpty());
    }

    @Test
    void handlesSupplementaryUnicodeAsSingleIndex() throws Exception {
        Path file = tempDir.resolve("emoji.txt");
        Files.writeString(file, "a😀b😀a", StandardCharsets.UTF_8);

        assertEquals(List.of(1L), SubstringFinder.find(file, "😀b"));
        assertEquals(List.of(2L), SubstringFinder.find(file, "b😀"));
        assertEquals(List.of(3L), SubstringFinder.find(file, "😀a"));
    }

    @Test
    void throwsOnEmptyNeedle() throws Exception {
        Path file = tempDir.resolve("x.txt");
        Files.writeString(file, "abc", StandardCharsets.UTF_8);

        assertThrows(IllegalArgumentException.class, () -> SubstringFinder.find(file, ""));
    }

    @Test
    void throwsOnNullNeedle() {
        assertThrows(NullPointerException.class, () -> SubstringFinder.find(Path.of("x"), null));
    }

    @Test
    void throwsOnNullFile() {
        assertThrows(NullPointerException.class, () -> SubstringFinder.find((Path) null, "a"));
    }

    @Test
    void worksOnLargeGeneratedFile() throws Exception {
        Path file = tempDir.resolve("big.txt");
        String needle = "NEEDLE";

        long firstNeedleIndex;
        long secondNeedleIndex;

        try (BufferedWriter w = Files.newBufferedWriter(file, StandardCharsets.UTF_8)) {
            int prefix = 100_000;
            for (int i = 0; i < prefix; i++) w.write('x');
            firstNeedleIndex = prefix;

            w.write(needle);

            int middle = 200_000;
            for (int i = 0; i < middle; i++) w.write('x');

            secondNeedleIndex = prefix + needle.codePointCount(0, needle.length()) + middle;
            w.write(needle);
        }

        assertEquals(List.of(firstNeedleIndex, secondNeedleIndex), SubstringFinder.find(file, needle));
    }
}
