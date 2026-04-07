package ru.nsu.bukhanov;

import ru.nsu.bukhanov.game.Game;
import ru.nsu.bukhanov.io.ConsoleIO;
import ru.nsu.bukhanov.model.Deck;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        DeckFactory deckFactory = new DeckFactory(1, new Random());
        new Game(new ConsoleIO(), deckFactory).start();
    }
}
