package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;
import ru.nsu.bukhanov.game.Game;
import ru.nsu.bukhanov.io.FakeIO;
import ru.nsu.bukhanov.model.*;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GameDealerBlackjackTest {
    @Test
    void dealerBlackjackPlayerLoses() {
        RiggedDeck deck = new RiggedDeck(Arrays.asList(
                new Card(Suit.SPADES, Rank.NINE),    // p1
                new Card(Suit.CLUBS, Rank.ACE),      // d1
                new Card(Suit.HEARTS, Rank.SEVEN),   // p2
                new Card(Suit.DIAMONDS, Rank.KING)   // d2 -> дилер BJ
        ));

        FakeIO io = new FakeIO();
        io.feed("0");
        Game game = new Game(io, 1, new Random(1), deck);
        game.start();
        String out = io.output();

        assertTrue(out.contains("У дилера Блэкджек. Вы проиграли раунд."));
        assertTrue(out.contains("Счет 0:1"));
    }
}
