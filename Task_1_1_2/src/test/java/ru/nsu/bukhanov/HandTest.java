package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;
import ru.nsu.bukhanov.model.Card;
import ru.nsu.bukhanov.model.Hand;
import ru.nsu.bukhanov.model.Rank;
import ru.nsu.bukhanov.model.Suit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HandTest {
    @Test void aceCountsAs11WhenNoBust() {
        Hand h = new Hand();
        h.add(new Card(Suit.SPADES, Rank.ACE));
        h.add(new Card(Suit.HEARTS, Rank.SIX));
        assertEquals(17, h.bestValue());
        assertFalse(h.isBust());
    }

    @Test void aceDowngradesTo1OnBust() {
        Hand h = new Hand();
        h.add(new Card(Suit.SPADES, Rank.ACE));
        h.add(new Card(Suit.CLUBS, Rank.NINE));
        h.add(new Card(Suit.DIAMONDS, Rank.THREE));
        assertEquals(13, h.bestValue());
        assertFalse(h.isBust());
    }

    @Test void multipleAcesDowngrade() {
        Hand h = new Hand();
        h.add(new Card(Suit.SPADES, Rank.ACE));
        h.add(new Card(Suit.CLUBS, Rank.ACE));
        h.add(new Card(Suit.DIAMONDS, Rank.NINE));
        assertEquals(21, h.bestValue());
        assertFalse(h.isBust());
    }

    @Test void detectsBlackjackAcePlusTen() {
        Hand h = new Hand();
        h.add(new Card(Suit.SPADES, Rank.ACE));
        h.add(new Card(Suit.HEARTS, Rank.KING));
        assertTrue(h.isBlackjack());
    }

    @Test void notBlackjackWhenMoreThanTwoCards() {
        Hand h = new Hand();
        h.add(new Card(Suit.SPADES, Rank.ACE));
        h.add(new Card(Suit.HEARTS, Rank.KING));
        h.add(new Card(Suit.CLUBS, Rank.TWO));
        assertFalse(h.isBlackjack());
    }
}
