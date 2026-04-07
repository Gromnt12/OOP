package ru.nsu.bukhanov;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class MainTest {

    @TempDir
    Path tempDir;

    @Test
    void mainWorksWithArgs() throws Exception {
        Path file = tempDir.resolve("input.txt");
        Files.writeString(file, "абракадабра", StandardCharsets.UTF_8);

        String out = runMain(
                new String[]{file.toString(), "бра"},
                null
        );

        assertTrue(out.contains("[1, 8]"));
    }

    @Test
    void mainWorksWithStdin() throws Exception {
        Path file = tempDir.resolve("input.txt");
        Files.writeString(file, "aaaa", StandardCharsets.UTF_8);

        String stdin = file.toString() + "\n" + "aa\n";
        String out = runMain(new String[]{}, stdin);

        assertTrue(out.contains("[0, 1, 2]"));
    }

    private static String runMain(String[] args, String stdin) throws Exception {
        InputStream oldIn = System.in;
        PrintStream oldOut = System.out;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(baos, true, StandardCharsets.UTF_8));

            if (stdin != null) {
                System.setIn(new ByteArrayInputStream(stdin.getBytes(StandardCharsets.UTF_8)));
            }

            Main.main(args);
            return baos.toString(StandardCharsets.UTF_8);
        } finally {
            System.setIn(oldIn);
            System.setOut(oldOut);
        }
    }
}
