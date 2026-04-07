package ru.nsu.bukhanov;

public final class Main {

    public static void main(String[] args) {
        GradeBook gb = new GradeBook(EducationForm.PAID);

        gb.setCurrentSemester(5);

        gb.addPlannedAssessment("Математика", AssessmentType.EXAM, 3);
        gb.addPlannedAssessment("ООП", AssessmentType.EXAM, 4);
        gb.addPlannedAssessment("Физкультура", AssessmentType.DIFF_CREDIT, 4);

        gb.recordGrade("Математика", AssessmentType.EXAM, 3, Grade.EXCELLENT);
        gb.recordGrade("ООП", AssessmentType.EXAM, 4, Grade.GOOD);
        gb.recordGrade("Физкультура", AssessmentType.DIFF_CREDIT, 4, Grade.SATISFACTORY);

        System.out.println("Текущий семестр: " + gb.getCurrentSemester());
        System.out.println("Форма обучения: " + gb.getEducationForm());
        System.out.printf("Средний балл: %.2f%n", gb.getCurrentAverageGrade());
        System.out.println("Можно перевестись на бюджет: " + gb.canTransferToBudget());
        System.out.println("Возможен красный диплом: " + gb.canGetHonoursDiploma());
        System.out.println("Возможна повышенная стипендия: " + gb.canGetIncreasedScholarship());
    }
}
