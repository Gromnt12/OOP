package ru.nsu.bukhanov.model;

import ru.nsu.bukhanov.DeckFactory;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

/** Тестовая колода: возвращает карты в заданном порядке. */
public class RiggedDeckFactory extends DeckFactory {
    private final Deque<Card> deck;

    public RiggedDeckFactory(List<Card> sequence) {
        super(1, new Random(1));
        this.deck = new ArrayDeque<>(sequence);
    }

    @Override
    public Deck create() {
        return new Deck(deck);
    }
}
