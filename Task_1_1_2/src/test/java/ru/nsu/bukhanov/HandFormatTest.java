package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;
import ru.nsu.bukhanov.model.*;

import static org.junit.jupiter.api.Assertions.*;

public class HandFormatTest {
    @Test
    void hideSecondCardFormat() {
        Hand h = new Hand();
        h.add(new Card(Suit.SPADES, Rank.QUEEN)); // "Дама Пики (10)"
        h.add(new Card(Suit.HEARTS, Rank.THREE)); // скрытая
        String s = h.toRuList(true);
        assertTrue(s.startsWith("[")); // начинается с [
        assertTrue(s.contains("<закрытая карта> ]")); // маркер скрытой + закрывающая скобка
    }

    @Test
    void fullListFormat() {
        Hand h = new Hand();
        h.add(new Card(Suit.SPADES, Rank.QUEEN));
        h.add(new Card(Suit.HEARTS, Rank.THREE));
        String s = h.toRuList(false);
        assertTrue(s.startsWith("["));
        assertTrue(s.endsWith("]"));
        assertTrue(s.contains("Дама Пики (10)"));
        assertTrue(s.contains("Тройка Червы (3)"));
    }
}
