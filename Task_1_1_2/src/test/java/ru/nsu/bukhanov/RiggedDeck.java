package ru.nsu.bukhanov.model;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

/** Тестовая колода: возвращает карты в заданном порядке. */
public class RiggedDeck extends Deck {
    private final Deque<Card> q;

    public RiggedDeck(List<Card> sequence) {
        super(1, new Random(1));
        this.q = new ArrayDeque<>(sequence);
    }

    @Override
    public Card draw() {
        if (q.isEmpty()) throw new NoSuchElementException("RiggedDeck пуста");
        return q.removeFirst();
    }

    @Override
    public int remaining() {
        return q.size();
    }
}
