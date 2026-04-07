package ru.nsu.bukhanov;

import java.util.Objects;

/**
 * Уникальный ключ аттестации: (предмет, тип аттестации).
 * Нужен, чтобы хранить/сравнивать оценки по одному предмету в разных формах (экзамен/дифзачёт и т.п.).
 */
public final class AssessmentKey {
    private final String subject;
    private final AssessmentType type;

    public AssessmentKey(String subject, AssessmentType type) {
        this.subject = normalizeSubject(subject);
        this.type = Objects.requireNonNull(type, "type must not be null");
    }

    public String subject() {
        return subject;
    }

    public AssessmentType type() {
        return type;
    }

    static String normalizeSubject(String subject) {
        if (subject == null) {
            throw new IllegalArgumentException("subject must not be null");
        }
        String s = subject.trim();
        if (s.isEmpty()) {
            throw new IllegalArgumentException("subject must not be blank");
        }
        return s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AssessmentKey)) return false;
        AssessmentKey that = (AssessmentKey) o;
        return subject.equals(that.subject) && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, type);
    }

    @Override
    public String toString() {
        return "AssessmentKey{" + "subject='" + subject + '\'' + ", type=" + type + '}';
    }
}
