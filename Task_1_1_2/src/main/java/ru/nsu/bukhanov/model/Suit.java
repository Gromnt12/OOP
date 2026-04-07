package ru.nsu.bukhanov.model;

public enum Suit {
    CLUBS("Трефы"),
    DIAMONDS("Бубны"),
    HEARTS("Червы"),
    SPADES("Пики");

    private final String ru;

    Suit(String ru) {
        this.ru = ru;
    }

    public String ru() {
        return ru;
    }
}
