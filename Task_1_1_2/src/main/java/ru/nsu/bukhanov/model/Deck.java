package ru.nsu.bukhanov.model;

import java.util.*;

public class Deck {
    private final Deque<Card> cards = new ArrayDeque<>();
    private final Random random;

    public Deck(int decksCount, Random random) {
        if (decksCount <= 0) throw new IllegalArgumentException("decksCount > 0");
        this.random = random == null ? new Random() : random;

        List<Card> tmp = new ArrayList<>();
        for (int d = 0; d < decksCount; d++) {
            for (Suit s : Suit.values()) {
                for (Rank r : Rank.values()) {
                    tmp.add(new Card(s, r));
                }
            }
        }
        Collections.shuffle(tmp, this.random);
        cards.addAll(tmp);
    }

    public Card draw() {
        if (cards.isEmpty()) throw new NoSuchElementException("Колода пуста");
        return cards.removeFirst();
    }

    public int remaining() { return cards.size(); }
}
