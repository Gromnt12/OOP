package ru.nsu.bukhanov.model;

import java.util.*;

public class Hand {
    private final List<Card> cards = new ArrayList<>();

    public void add(Card c) {
        cards.add(c);
    }

    public List<Card> cards() {
        return Collections.unmodifiableList(cards);
    }

    public int bestValue() {
        int sum = 0;
        int aces = 0;
        for (Card c : cards) {
            sum += c.baseValue();
            if (c.rank().isAce()) aces++;
        }
        // корректируем тузы: пока перебор (>21) и есть тузы — уменьшаем по 10 (11 -> 1)
        while (sum > 21 && aces > 0) {
            sum -= 10;
            aces--;
        }
        return sum;
    }

    public boolean isBlackjack() {
        if (cards.size() != 2) return false;
        boolean hasAce = cards.get(0).rank().isAce() || cards.get(1).rank().isAce();
        boolean hasTenVal = cards.get(0).rank().isTenValue() || cards.get(1).rank().isTenValue();
        return hasAce && hasTenVal;
    }

    public boolean isBust() {
        return bestValue() > 21;
    }

    public String toRuList(boolean hideSecond) {
    StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < cards.size(); i++) {
            if (i > 0) sb.append(", ");
            if (hideSecond && i == 1) {
                sb.append("<закрытая карта> ]");
                return sb.toString();
            } else {
                sb.append(cards.get(i).toRu());
            }
        }
        sb.append("]");
        return sb.toString();
    }

}