package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;
import ru.nsu.bukhanov.game.Game;
import ru.nsu.bukhanov.io.FakeIO;
import ru.nsu.bukhanov.model.*;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GamePlayerBustTest {
    @Test
    void playerBustsOnHit() {
        RiggedDeck deck = new RiggedDeck(Arrays.asList(
                new Card(Suit.SPADES, Rank.KING),    // p1 (10)
                new Card(Suit.CLUBS, Rank.SIX),      // d1 (6)
                new Card(Suit.HEARTS, Rank.NINE),    // p2 (19)
                new Card(Suit.DIAMONDS, Rank.FIVE),  // d2 (11)
                new Card(Suit.SPADES, Rank.THREE)    // p hit -> 22 (перебор)
        ));

        FakeIO io = new FakeIO();
        io.feed("1", "0"); // взять карту, затем не продолжать игру
        Game game = new Game(io, 1, new Random(1), deck);
        game.start();
        String out = io.output();

        assertTrue(out.contains("Перебор! Вы проиграли раунд."));
        assertTrue(out.contains("Счет 0:1"));
    }
}
