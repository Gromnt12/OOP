package ru.nsu.bukhanov;

import ru.nsu.bukhanov.model.Deck;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
    @Test
    void buildsSingleDeckWith52Cards() {
        Deck deck = new Deck(1, new Random(42));
        int count = 0;
        while (deck.remaining() > 0) { deck.draw(); count++; }
        assertEquals(52, count);
    }

    @Test
    void throwsWhenEmpty() {
        Deck deck = new Deck(1, new Random(1));
        for (int i = 0; i < 52; i++) deck.draw();
        assertThrows(NoSuchElementException.class, deck::draw);
    }
}
