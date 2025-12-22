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

    /**
     * TODO
     * Если после старшего суррогата (high surrogate, U+D800-U+DBFF) в последовательности идет не младший суррогат (low surrogate, U
     * +DC00-U+DFFF), а другой байт, то это невалидная последовательность Юникода (Unicode), скорее всего, из-за ошибки в данных,
     * обрыва передачи или неправильного декодирования, так как старший суррогат требует обязательного продолжения младшим для
     * формирования полного символа (>= 10000_16). В кодировках вроде UTF-8 (где байты 1-4) или UTF-16 (где пары байт для суррогатов) это
     * нарушает структуру, и программы выдают ошибку "surrogates not allowed" или похожую, пытаясь обработать это как отдельный симво
     *
     * @param file
     * @return
     * @throws IOException
     */
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
