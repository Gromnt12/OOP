package ru.nsu.bukhanov.game;

import org.junit.jupiter.api.Test;
import ru.nsu.bukhanov.io.FakeIO;
import ru.nsu.bukhanov.model.*;
import ru.nsu.bukhanov.model.RiggedDeck;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GameDealerBlackjackTest {
    private final static RiggedDeck RIGGED_DECK = new RiggedDeck(Arrays.asList(
            new Card(Suit.SPADES, Rank.NINE),    // p1
            new Card(Suit.CLUBS, Rank.ACE),      // d1
            new Card(Suit.HEARTS, Rank.SEVEN),   // p2
            new Card(Suit.DIAMONDS, Rank.KING)   // d2 -> дилер BJ
    ));

    static class TestGame extends Game {
        public TestGame(FakeIO io, int i, Random random) {
            super(io, i, random);
        }

        Deck buildDeck() {
            return RIGGED_DECK;
        }
    }

    @Test
    void dealerBlackjackPlayerLoses() {

        FakeIO io = new FakeIO();
        io.feed("0");
        TestGame game = new TestGame(io, 1, new Random(1));
        game.start();
        String out = io.output();

        assertTrue(out.contains("У дилера Блэкджек. Вы проиграли раунд."));
        assertTrue(out.contains("Счет 0:1"));
    }
}
