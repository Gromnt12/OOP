package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;
import ru.nsu.bukhanov.game.Game;
import ru.nsu.bukhanov.io.FakeIO;
import ru.nsu.bukhanov.model.*;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class GamePlayerBlackjackTest {
    @Test
    void playerBlackjackWinsImmediately() {
        // Порядок выдачи:
        // p1, d1, p2, d2, ...
        RiggedDeck deck = new RiggedDeck(Arrays.asList(
                new Card(Suit.SPADES, Rank.ACE),     // p1
                new Card(Suit.CLUBS, Rank.NINE),     // d1
                new Card(Suit.HEARTS, Rank.KING),    // p2 -> BJ
                new Card(Suit.DIAMONDS, Rank.THREE)  // d2
        ));

        FakeIO io = new FakeIO();
        io.feed("0"); // не продолжаем второй раунд
        Game game = new Game(io, 1, new Random(1), deck);
        game.start();
        String out = io.output();

        assertTrue(out.contains("У вас Блэкджек! Вы выиграли раунд!"));
        assertTrue(out.contains("Счет 1:0")); 
        assertTrue(out.contains("Спасибо за игру!"));
    }
}
