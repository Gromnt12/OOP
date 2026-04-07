package ru.nsu.bukhanov.participants;

import ru.nsu.bukhanov.io.IO;
import ru.nsu.bukhanov.model.Card;
import ru.nsu.bukhanov.model.Deck;

public class Dealer extends Participant {
    public void play(Deck deck, IO io) {
        while (score() < 17) {
            Card c = deck.draw();
            hand.add(c);
            io.println("Дилер открывает карту " + c.toRu());
        }
    }
}
