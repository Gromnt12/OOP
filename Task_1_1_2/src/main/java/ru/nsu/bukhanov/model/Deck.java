package ru.nsu.bukhanov.model;

import java.util.*;

public class Deck {
    private  final Deque<Card> cards;

    public Deck(Deque<Card> cards) {
        this.cards = cards;
    }

    public Card draw() {
        if (cards.isEmpty()) throw new NoSuchElementException("Колода пуста");
        return cards.removeFirst();
    }

    public int remaining() { return cards.size(); }
}
