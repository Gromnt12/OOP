package ru.nsu.bukhanov;

import java.util.Objects;

/**
 * Одна попытка сдачи аттестации (включая пересдачи).
 * Для определения "последней" попытки используется (семестр, sequence).
 */
public final class AssessmentAttempt {
    private final AssessmentKey key;
    private final int semester;
    private final Grade grade;
    private final long sequence; // монотонно растёт внутри GradeBook, чтобы стабильно определять "последнюю запись"

    public AssessmentAttempt(AssessmentKey key, int semester, Grade grade, long sequence) {
        this.key = Objects.requireNonNull(key, "key must not be null");
        if (semester <= 0) {
            throw new IllegalArgumentException("semester must be > 0");
        }
        this.semester = semester;
        this.grade = Objects.requireNonNull(grade, "grade must not be null");
        if (sequence < 0) {
            throw new IllegalArgumentException("sequence must be >= 0");
        }
        this.sequence = sequence;
    }

    public AssessmentKey key() {
        return key;
    }

    public int semester() {
        return semester;
    }

    public Grade grade() {
        return grade;
    }

    public long sequence() {
        return sequence;
    }
}
