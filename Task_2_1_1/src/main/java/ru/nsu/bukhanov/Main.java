package ru.nsu.bukhanov;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        // Определяем N  из аргументов командной строки
        int nFromArgs = 10;
        if (args.length > 0) {
            try {
                nFromArgs = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("Ошибка: Аргумент должен быть числом. Ставлю N=10");
            }
        }
        long[] data = new long[200];
        Arrays.fill(data, 2147483647L); // Число 2^31 - 1 (простое)
        for (int i = 0; i < 5; i++) {
            new ThreadFinder(4).hasNonPrime(data);
        }

        runAllMeasurements(data, nFromArgs);
    }

    public static void runAllMeasurements(long[] data, int N) {
        int[] threadCounts = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        System.out.println("Implementation,Run,TimeMS");

        for (int i = 0; i < N; i++) {
            System.out.println("Seq," + i + "," + measureSilent(new SequentialFinder(), data));
        }
        for (int count : threadCounts) {
            for (int i = 0; i < N; i++) {
                System.out.println("T" + count + "," + i + "," + measureSilent(new ThreadFinder(count), data));
            }
        }

        for (int i = 0; i < N; i++) {
            System.out.println("Stream," + i + "," + measureSilent(new StreamFinder(), data));
        }
    }
    private static long measureSilent(NonPrimeFinder finder, long[] data) {
        long start = System.currentTimeMillis();
        finder.hasNonPrime(data);
        long end = System.currentTimeMillis();
        return (end - start);
    }
}