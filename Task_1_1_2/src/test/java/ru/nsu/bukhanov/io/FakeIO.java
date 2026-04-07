package ru.nsu.bukhanov.io;

import java.util.ArrayDeque;
import java.util.Queue;

public class FakeIO implements IO {
    private final Queue<String> input = new ArrayDeque<>();
    private final StringBuilder out = new StringBuilder();

    public void feed(String... lines) { for (String s : lines) input.add(s); }
    public String output() { return out.toString(); }

    @Override public void println(String s) { out.append(s).append(System.lineSeparator()); }
    @Override public void print(String s) { out.append(s); }
    @Override public String readLine() { return input.isEmpty() ? "" : input.remove(); }
}
