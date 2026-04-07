package ru.nsu.bukhanov;

import ru.nsu.bukhanov.model.Deck;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
    @Test
    void buildsSingleDeckWith52Cards() {
        DeckFactory deckFactory = new DeckFactory(1, new Random(42));
        Deck deck = deckFactory.create();
        int count = 0;
        while (deck.remaining() > 0) { deck.draw(); count++; }
        assertEquals(52, count);
    }

    @Test
    void throwsWhenEmpty() {
        DeckFactory deckFactory = new DeckFactory(1, new Random(1));
        Deck deck = deckFactory.create();
        for (int i = 0; i < 52; i++) deck.draw();
        assertThrows(NoSuchElementException.class, deck::draw);
    }
}
