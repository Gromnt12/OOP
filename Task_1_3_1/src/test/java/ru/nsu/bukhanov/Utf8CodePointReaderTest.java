package ru.nsu.bukhanov;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class Utf8CodePointReaderTest {

    @TempDir
    Path tempDir;

    @Test
    void readsCodePointsIncludingSurrogatePairs() throws Exception {
        Path file = tempDir.resolve("cp.txt");
        Files.writeString(file, "A😀Б", StandardCharsets.UTF_8);

        try (Utf8CodePointReader r = Utf8CodePointReader.open(file)) {
            assertEquals((int) 'A', r.nextCodePoint());
            assertEquals("😀".codePointAt(0), r.nextCodePoint());
            assertEquals((int) 'Б', r.nextCodePoint());
            assertEquals(-1, r.nextCodePoint());
        }
    }
}
