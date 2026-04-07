package ru.nsu.bukhanov;

/**
 * Оценка по пятибалльной системе (2..5).
 */
public enum Grade {
    UNSATISFACTORY(2),
    SATISFACTORY(3),
    GOOD(4),
    EXCELLENT(5);

    private final int value;

    Grade(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public boolean isSatisfactoryOrWorse() {
        return value <= SATISFACTORY.value;
    }

    public boolean isExcellent() {
        return this == EXCELLENT;
    }
}
