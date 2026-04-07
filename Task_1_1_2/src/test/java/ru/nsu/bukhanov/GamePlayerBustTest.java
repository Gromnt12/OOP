package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;
import ru.nsu.bukhanov.game.Game;
import ru.nsu.bukhanov.io.FakeIO;
import ru.nsu.bukhanov.model.Card;
import ru.nsu.bukhanov.model.Rank;
import ru.nsu.bukhanov.model.RiggedDeckFactory;
import ru.nsu.bukhanov.model.Suit;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GamePlayerBustTest {
    @Test
    void playerBustsOnHit() {
        RiggedDeckFactory deckFactory = new RiggedDeckFactory(Arrays.asList(
                new Card(Suit.SPADES, Rank.KING),    // p1 (10)
                new Card(Suit.CLUBS, Rank.SIX),      // d1 (6)
                new Card(Suit.HEARTS, Rank.NINE),    // p2 (19)
                new Card(Suit.DIAMONDS, Rank.FIVE),  // d2 (11)
                new Card(Suit.SPADES, Rank.THREE)    // p hit -> 22 (перебор)
        ));

        FakeIO io = new FakeIO();
        io.feed("1", "0"); // взять карту, затем не продолжать игру
        Game game = new Game(io, deckFactory);
        game.start();
        String out = io.output();

        assertTrue(out.contains("Перебор! Вы проиграли раунд."));
        assertTrue(out.contains("Счет 0:1"));
    }
}
