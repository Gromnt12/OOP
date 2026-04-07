package ru.nsu.bukhanov.model;

public enum Rank {
    TWO("Двойка", 2),
    THREE("Тройка", 3),
    FOUR("Четверка", 4),
    FIVE("Пятерка", 5),
    SIX("Шестерка", 6),
    SEVEN("Семерка", 7),
    EIGHT("Восьмерка", 8),
    NINE("Девятка", 9),
    TEN("Десятка", 10),
    JACK("Валет", 10),
    QUEEN("Дама", 10),
    KING("Король", 10),
    ACE("Туз", 11);

    private final String ru;
    private final int points;

    Rank(String ru, int points) {
        this.ru = ru;
        this.points = points;
    }

    public String ru() {
        return ru;
    }

    public int points() {
        return points;
    }

    public boolean isAce() {
        return this == ACE;
    }

    public boolean isTenValue() {
        return points == 10;
    }
}
