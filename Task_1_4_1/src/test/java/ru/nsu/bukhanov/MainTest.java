package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void mainRunsAndPrintsSomething() {
        PrintStream oldOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));

        try {
            assertDoesNotThrow(() -> Main.main(new String[0]));
            String out = buffer.toString();
            assertTrue(out.contains("Средний балл"));
            assertTrue(out.contains("Можно перевестись на бюджет"));
        } finally {
            System.setOut(oldOut);
        }
    }
}
