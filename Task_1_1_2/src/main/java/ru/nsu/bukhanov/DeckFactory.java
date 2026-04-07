package ru.nsu.bukhanov;

import ru.nsu.bukhanov.model.Card;
import ru.nsu.bukhanov.model.Deck;
import ru.nsu.bukhanov.model.Rank;
import ru.nsu.bukhanov.model.Suit;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Random;

public class DeckFactory {
    private final int count;
    private final Random random;

    public DeckFactory(int count, Random random) {
        if (count <= 0) {
            throw new IllegalArgumentException("decksCount > 0");
        }
        if (random == null) {
            throw new IllegalArgumentException("random can not be null");
        }
        this.count = count;
        this.random = random;
    }

    public Deck create() {

        List<Card> tmp = new ArrayList<>();
        for (int d = 0; d < count; d++) {
            for (Suit s : Suit.values()) {
                for (Rank r : Rank.values()) {
                    tmp.add(new Card(s, r));
                }
            }
        }
        Collections.shuffle(tmp, this.random);
        Deque<Card> cards = new ArrayDeque<>();
        cards.addAll(tmp);

        return new Deck(cards);
    }
}
