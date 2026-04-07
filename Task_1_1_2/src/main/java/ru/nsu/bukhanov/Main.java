package ru.nsu.bukhanov;

import ru.nsu.bukhanov.game.Game;
import ru.nsu.bukhanov.io.ConsoleIO;

public class Main {
    public static void main(String[] args) {
        new Game(new ConsoleIO(), 1, null).start();
    }
}
