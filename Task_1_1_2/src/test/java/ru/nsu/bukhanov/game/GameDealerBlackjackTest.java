package ru.nsu.bukhanov.game;

import org.junit.jupiter.api.Test;
import ru.nsu.bukhanov.io.FakeIO;
import ru.nsu.bukhanov.model.Card;
import ru.nsu.bukhanov.model.Rank;
import ru.nsu.bukhanov.model.RiggedDeckFactory;
import ru.nsu.bukhanov.model.Suit;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameDealerBlackjackTest {
    private final static RiggedDeckFactory RIGGED_DECK_FACTORY = new RiggedDeckFactory(Arrays.asList(
            new Card(Suit.SPADES, Rank.NINE),    // p1
            new Card(Suit.CLUBS, Rank.ACE),      // d1
            new Card(Suit.HEARTS, Rank.SEVEN),   // p2
            new Card(Suit.DIAMONDS, Rank.KING)   // d2 -> дилер BJ
    ));

    @Test
    void dealerBlackjackPlayerLoses() {

        FakeIO io = new FakeIO();
        io.feed("0");
        Game game = new Game(io, RIGGED_DECK_FACTORY);
        game.start();
        String out = io.output();

        assertTrue(out.contains("У дилера Блэкджек. Вы проиграли раунд."));
        assertTrue(out.contains("Счет 0:1"));
    }
}
