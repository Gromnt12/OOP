package ru.nsu.bukhanov.model;

import java.util.Objects;

public class Card {
    private final Suit suit;
    private final Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = Objects.requireNonNull(suit);
        this.rank = Objects.requireNonNull(rank);
    }

    public Suit suit() {
        return suit;
    }

    public Rank rank() {
        return rank;
    }

    public int baseValue() {
        return rank.points();
    }

    public String toRu() {
        return rank.ru() + " " + suit.ru() + " (" + baseValue() + ")";
    }

    @Override
    public String toString() {
        return toRu();
    }
}