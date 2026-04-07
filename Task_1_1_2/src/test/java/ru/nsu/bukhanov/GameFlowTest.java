package ru.nsu.bukhanov;

import ru.nsu.bukhanov.game.Game;
import ru.nsu.bukhanov.io.FakeIO;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GameFlowTest {

    @Test
    void oneRoundStopsAndPrintsScore() {
        FakeIO io = new FakeIO();
        io.feed("0", "0");
        Game game = new Game(io, new DeckFactory(1, new Random(7)));
        game.start();

        String out = io.output();
        assertTrue(out.contains("Добро пожаловать в Блэкджек!"));
        assertTrue(out.contains("Раунд 1"));
        // не проверяем "Ход дилера" — раздача может завершиться блэкджеком
        assertTrue(out.contains("Счет"));              
        assertTrue(out.contains("Спасибо за игру!")); 
    }
}
