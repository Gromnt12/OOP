package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;
import ru.nsu.bukhanov.game.Game;
import ru.nsu.bukhanov.io.FakeIO;
import ru.nsu.bukhanov.model.*;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GameDealerBustTest {
    @Test
    void dealerBustsAndPlayerWins() {
        RiggedDeck deck = new RiggedDeck(Arrays.asList(
                new Card(Suit.SPADES, Rank.FIVE),     // p1 (5)
                new Card(Suit.CLUBS, Rank.SEVEN),     // d1 (7)
                new Card(Suit.HEARTS, Rank.NINE),     // p2 (14)
                new Card(Suit.DIAMONDS, Rank.SEVEN),  // d2 (14)
                new Card(Suit.SPADES, Rank.TEN)       // дилер добирает -> 24
        ));

        FakeIO io = new FakeIO();
        io.feed("0", "0"); // игрок сразу стоп, затем не продолжаем
        Game game = new Game(io, 1, new Random(1), deck);
        game.start();
        String out = io.output();

        assertTrue(out.contains("Дилер перебрал. Вы выиграли раунд!"));
        assertTrue(out.contains("Счет 1:0"));
    }
}
