package ru.nsu.bukhanov;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

public final class Main {

    private Main() {
        // no instances
    }
    public static void main(String[] args) throws Exception {
        String file;
        String needle;

        if (args.length >= 2) {
            file = args[0];
            needle = args[1];
        } else {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
                file = br.readLine();
                needle = br.readLine();
            }
            if (file == null || needle == null) {
                System.err.println("Usage: <file> <needle> OR stdin: file\\nneedle");
                System.exit(1);
                return;
            }
        }

        List<Long> positions = SubstringFinder.find(Path.of(file), needle);

        // Формат как в примере задания: [1, 8]
        System.out.println(positions);
    }
}
