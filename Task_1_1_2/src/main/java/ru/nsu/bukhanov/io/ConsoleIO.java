package ru.nsu.bukhanov.io;

import java.util.Scanner;

public class ConsoleIO implements IO {
    private final Scanner scanner = new Scanner(System.in);

    @Override public void println(String s) { System.out.println(s); }
    @Override public void print(String s) { System.out.print(s); }
    @Override public String readLine() { return scanner.nextLine(); }
}
