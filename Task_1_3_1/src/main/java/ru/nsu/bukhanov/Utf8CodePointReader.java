package ru.nsu.bukhanov;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * Потоковый reader, который возвращает Unicode code points из UTF-8 файла.
 */
public final class Utf8CodePointReader implements Closeable {

    private final PushbackReader reader;

    private Utf8CodePointReader(PushbackReader reader) {
        this.reader = reader;
    }

    public static Utf8CodePointReader open(Path file) throws IOException {
        Objects.requireNonNull(file, "file must not be null");
        var in = new BufferedInputStream(Files.newInputStream(file));
        var isr = new InputStreamReader(in, StandardCharsets.UTF_8);
        return new Utf8CodePointReader(new PushbackReader(isr, 2));
    }

    public int nextCodePoint() throws IOException {
        int first = reader.read();
        if (first == -1) {
            return -1;
        }

        char c1 = (char) first;

        if (Character.isHighSurrogate(c1)) {
            int second = reader.read();
            if (second == -1) {
                return c1;
            }
            char c2 = (char) second;
            if (Character.isLowSurrogate(c2)) {
                return Character.toCodePoint(c1, c2);
            }
            reader.unread(second);
            return c1;
        }

        return c1;
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
