package ru.nsu.bukhanov;

import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadFinder implements NonPrimeFinder {
    private final int threadCount;

    public ThreadFinder(int threadCount) {
        this.threadCount = threadCount;
    }

    @Override
    public boolean hasNonPrime(long[] numbers) {
        AtomicBoolean found = new AtomicBoolean(false);
        Thread[] threads = new Thread[threadCount];
        int chunkSize = (numbers.length + threadCount - 1) / threadCount;

        for (int i = 0; i < threadCount; i++) {
            int start = i * chunkSize;
            int end = Math.min(start + chunkSize, numbers.length);
            threads[i] = new Thread(() -> {
                for (int j = start; j < end && !found.get(); j++) {
                    if (isNotPrime(numbers[j])) found.set(true);
                }
            });
            threads[i].start();
        }

        try {
            for (Thread t : threads) t.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return found.get();
    }
}