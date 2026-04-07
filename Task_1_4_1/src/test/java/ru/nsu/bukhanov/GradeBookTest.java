package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GradeBookTest {

    @Test
    void averageIsZeroWhenNoGrades() {
        GradeBook gb = new GradeBook(EducationForm.BUDGET);
        assertEquals(0.0, gb.getCurrentAverageGrade(), 1e-9);
    }

    @Test
    void averageUsesLatestGradeForEachAssessmentKey() {
        GradeBook gb = new GradeBook(EducationForm.BUDGET);

        gb.recordGrade("Math", AssessmentType.EXAM, 1, Grade.GOOD);          // 4
        gb.recordGrade("OOP", AssessmentType.EXAM, 1, Grade.EXCELLENT);      // 5
        gb.recordGrade("Math", AssessmentType.EXAM, 2, Grade.EXCELLENT);     // позже -> 5

        assertEquals(5.0, gb.getCurrentAverageGrade(), 1e-9);
    }

    @Test
    void transferToBudgetIsFalseIfAlreadyBudget() {
        GradeBook gb = new GradeBook(EducationForm.BUDGET);
        gb.setCurrentSemester(5);
        gb.recordGrade("Math", AssessmentType.EXAM, 4, Grade.EXCELLENT);
        assertFalse(gb.canTransferToBudget());
    }

    @Test
    void transferToBudgetChecksOnlyExamsInLastTwoSessions() {
        GradeBook gb = new GradeBook(EducationForm.PAID);
        gb.setCurrentSemester(5); // последние две завершённые: 4 и 3

        gb.recordGrade("History", AssessmentType.EXAM, 2, Grade.SATISFACTORY);       // старое, не учитываем
        gb.recordGrade("PE", AssessmentType.DIFF_CREDIT, 4, Grade.SATISFACTORY);    // дифзачёт, допускается
        gb.recordGrade("Math", AssessmentType.EXAM, 4, Grade.GOOD);                 // ок
        gb.recordGrade("OOP", AssessmentType.EXAM, 3, Grade.EXCELLENT);             // ок

        assertTrue(gb.canTransferToBudget());

        gb.recordGrade("Physics", AssessmentType.EXAM, 3, Grade.SATISFACTORY);      // дисквалификация
        assertFalse(gb.canTransferToBudget());
    }

    @Test
    void honoursDiplomaFailsOnSatisfactoryFinalGrade() {
        GradeBook gb = new GradeBook(EducationForm.BUDGET);
        gb.addPlannedAssessment("Math", AssessmentType.EXAM);
        gb.addPlannedAssessment("OOP", AssessmentType.EXAM);

        gb.recordGrade("Math", AssessmentType.EXAM, 1, Grade.SATISFACTORY);
        gb.recordGrade("OOP", AssessmentType.EXAM, 1, Grade.EXCELLENT);

        assertFalse(gb.canGetHonoursDiploma());
    }

    @Test
    void honoursDiplomaFailsOnNonExcellentThesisIfPresent() {
        GradeBook gb = new GradeBook(EducationForm.BUDGET);
        gb.addPlannedAssessment("Math", AssessmentType.EXAM);
        gb.recordGrade("Math", AssessmentType.EXAM, 1, Grade.EXCELLENT);

        gb.setCurrentSemester(8);
        gb.recordThesisGrade(Grade.GOOD);

        assertFalse(gb.canGetHonoursDiploma());
    }

    @Test
    void honoursDiplomaIsForecastableWithMissingGrades() {
        GradeBook gb = new GradeBook(EducationForm.BUDGET);

        gb.addPlannedAssessment("A", AssessmentType.EXAM);
        gb.addPlannedAssessment("B", AssessmentType.EXAM);
        gb.addPlannedAssessment("C", AssessmentType.DIFF_CREDIT);
        gb.addPlannedAssessment("D", AssessmentType.DIFF_CREDIT);

        gb.recordGrade("A", AssessmentType.EXAM, 1, Grade.EXCELLENT);
        gb.recordGrade("B", AssessmentType.EXAM, 1, Grade.EXCELLENT);
        gb.recordGrade("C", AssessmentType.DIFF_CREDIT, 1, Grade.GOOD);

        assertTrue(gb.canGetHonoursDiploma());

        GradeBook gb2 = new GradeBook(EducationForm.BUDGET);
        gb2.addPlannedAssessment("A", AssessmentType.EXAM);
        gb2.addPlannedAssessment("B", AssessmentType.EXAM);
        gb2.addPlannedAssessment("C", AssessmentType.DIFF_CREDIT);
        gb2.addPlannedAssessment("D", AssessmentType.DIFF_CREDIT);

        gb2.recordGrade("A", AssessmentType.EXAM, 1, Grade.EXCELLENT);
        gb2.recordGrade("B", AssessmentType.EXAM, 1, Grade.GOOD);
        gb2.recordGrade("C", AssessmentType.DIFF_CREDIT, 1, Grade.GOOD);

        assertFalse(gb2.canGetHonoursDiploma());
    }

    @Test
    void increasedScholarshipChecksPreviousSemesterSession() {
        GradeBook gb = new GradeBook(EducationForm.BUDGET);
        gb.setCurrentSemester(3);

        gb.addPlannedAssessment("Math", AssessmentType.EXAM, 2);
        gb.addPlannedAssessment("OOP", AssessmentType.EXAM, 2);

        gb.recordGrade("Math", AssessmentType.EXAM, 2, Grade.EXCELLENT);
        assertTrue(gb.canGetIncreasedScholarship()); // OOP ещё не выставлен -> считаем "возможно"

        gb.recordGrade("OOP", AssessmentType.EXAM, 2, Grade.GOOD);
        assertFalse(gb.canGetIncreasedScholarship());
    }

    @Test
    void validationsWork() {
        GradeBook gb = new GradeBook(EducationForm.BUDGET);

        assertThrows(IllegalArgumentException.class, () -> gb.setCurrentSemester(0));
        assertThrows(IllegalArgumentException.class, () -> gb.addPlannedAssessment("   ", AssessmentType.EXAM));
        assertThrows(NullPointerException.class, () -> new GradeBook(null));
        assertThrows(IllegalArgumentException.class, () -> gb.recordGrade("Math", AssessmentType.EXAM, 0, Grade.GOOD));
        assertThrows(IllegalArgumentException.class, () -> gb.addPlannedAssessment("Math", AssessmentType.EXAM, -1));

        gb.addPlannedAssessment("Math", AssessmentType.EXAM);
        assertThrows(IllegalArgumentException.class, () -> gb.addPlannedAssessment("Math", AssessmentType.EXAM));
    }
}
