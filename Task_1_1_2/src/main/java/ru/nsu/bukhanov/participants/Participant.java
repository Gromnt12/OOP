package ru.nsu.bukhanov.participants;

import ru.nsu.bukhanov.model.Hand;
public abstract class Participant {
    protected final Hand hand = new Hand();

    public Hand hand() {
        return hand;
    }

    public int score() {
        return hand.bestValue();
    }

    public boolean bust() {
        return hand.isBust();
    }

    public boolean blackjack() {
        return hand.isBlackjack();
    }
}