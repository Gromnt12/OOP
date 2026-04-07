package ru.nsu.bukhanov;

import java.util.Objects;

/**
 * Запись учебного плана: какая аттестация планируется (и при желании в каком семестре).
 */
public final class PlannedAssessment {
    private final AssessmentKey key;
    private final int plannedSemester; // 0 означает "не указан / неизвестен"

    public PlannedAssessment(String subject, AssessmentType type, int plannedSemester) {
        if (plannedSemester < 0) {
            throw new IllegalArgumentException("plannedSemester must be >= 0");
        }
        this.key = new AssessmentKey(subject, type);
        this.plannedSemester = plannedSemester;
    }
    public AssessmentKey key() {
        return key;
    }

    public int plannedSemester() {
        return plannedSemester;
    }

    @Override
    public String toString() {
        return "PlannedAssessment{" + "key=" + key + ", plannedSemester=" + plannedSemester + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PlannedAssessment)) return false;
        PlannedAssessment other = (PlannedAssessment) o;
        return plannedSemester == other.plannedSemester && Objects.equals(key, other.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, plannedSemester);
    }
}
