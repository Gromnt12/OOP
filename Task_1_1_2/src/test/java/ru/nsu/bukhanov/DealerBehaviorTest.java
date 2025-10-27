package ru.nsu.bukhanov;

import ru.nsu.bukhanov.io.FakeIO;
import ru.nsu.bukhanov.io.IO;
import ru.nsu.bukhanov.model.Deck;
import ru.nsu.bukhanov.participants.Dealer;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DealerBehaviorTest {
    @Test
    void dealerHitsUntil17OrMore() {
        Deck deck = new Deck(1, new Random(123));
        Dealer dealer = new Dealer();
        IO io = new FakeIO();

        dealer.hand().add(deck.draw());
        dealer.hand().add(deck.draw());

        int before = dealer.score();
        dealer.play(deck, io);
        int after = dealer.score();

        assertTrue(after >= 17);
        assertTrue(after >= before);
    }
}
