package ru.nsu.bukhanov;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
Электронная зачётка студента ФИТ.

Реализует вычисления:
- текущий средний балл
- возможность перевода с платного на бюджет
- возможность получения красного диплома
- возможность получения повышенной стипендии в текущем семестре

Замечания:
- Средний балл считается по последней оценке для каждой пары (предмет, тип аттестации),
  плюс ВКР, если она записана.
- Перевод на бюджет проверяет только экзамены в двух последних завершённых сессиях:
  семестры currentSemester-1 и currentSemester-2.
- Красный диплом проверяется с прогнозом: если какие-то оценки из плана ещё не выставлены,
  считаем, что их теоретически можно получить на "отлично" и проверяем, достижимы ли 75%.
- Повышенная стипендия в текущем семестре определяется по результатам предыдущего семестра.
  Требование: все оценки в той сессии - "отлично".
  Если часть оценок ещё не проставлена, считаем "возможно", пока нет зафиксированной оценки отличающейся от отличной.
*/

public final class GradeBook {
    private final EducationForm educationForm;
    private int currentSemester = 1;

    private final Map<AssessmentKey, PlannedAssessment> plan = new LinkedHashMap<>();

    private final List<AssessmentAttempt> attempts = new ArrayList<>();

    private long seq = 0;


    public GradeBook(EducationForm educationForm) {
        this.educationForm = Objects.requireNonNull(educationForm, "educationForm must not be null");
    }


    public void setCurrentSemester(int semester) {
        if (semester <= 0) {
            throw new IllegalArgumentException("semester must be > 0");
        }
        this.currentSemester = semester;
    }

    public int getCurrentSemester() {
        return currentSemester;
    }

    public EducationForm getEducationForm() {
        return educationForm;
    }


    public void addPlannedAssessment(String subject, AssessmentType type) {
        addPlannedAssessment(subject, type, 0);
    }

    /**
     * Добавляет планируемую аттестацию.
     */
    public void addPlannedAssessment(String subject, AssessmentType type, int plannedSemester) {
        PlannedAssessment pa = new PlannedAssessment(subject, type, plannedSemester);
        if (plan.containsKey(pa.key())) {
            throw new IllegalArgumentException("planned assessment already exists: " + pa.key());
        }
        plan.put(pa.key(), pa);
    }

    /**
     * Записывает оценку. Поддерживает пересдачи:
     * допускаются несколько попыток для одного предмета.
     */
    public void recordGrade(String subject, AssessmentType type, int semester, Grade grade) {
        AssessmentKey key = new AssessmentKey(subject, type);
        attempts.add(new AssessmentAttempt(key, semester, grade, seq++));
    }

    public void recordThesisGrade(Grade grade) {
        recordGrade("THESIS", AssessmentType.THESIS, currentSemester, grade);
    }

    public double getCurrentAverageGrade() {
        Map<AssessmentKey, AssessmentAttempt> latest = latestAttemptsOverall();
        if (latest.isEmpty()) {
            return 0.0;
        }
        long sum = 0;
        long count = 0;
        for (AssessmentAttempt a : latest.values()) {
            sum += a.grade().value();
            count++;
        }
        return (double) sum / (double) count;
    }

    public boolean canTransferToBudget() {
        if (educationForm != EducationForm.PAID) {
            return false;
        }

        int s1 = currentSemester - 1;
        int s2 = currentSemester - 2;

        for (AssessmentAttempt a : attempts) {
            if (a.key().type() != AssessmentType.EXAM) {
                continue; // для перевода учитываем только экзамены
            }
            if (a.semester() == s1 || a.semester() == s2) {
                if (a.grade().isSatisfactoryOrWorse()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean canGetHonoursDiploma() {
        Map<AssessmentKey, AssessmentAttempt> latest = latestAttemptsOverall();

        // Правило по ВКР
        AssessmentKey thesisKey = new AssessmentKey("THESIS", AssessmentType.THESIS);
        AssessmentAttempt thesis = latest.get(thesisKey);
        if (thesis != null && !thesis.grade().isExcellent()) {
            return false;
        }

        // Что учитывать в "приложении": либо план (если есть), либо то, что реально встречалось в попытках
        Set<AssessmentKey> keysToCount = plannedOrObservedNonThesisKeys();

        int total = keysToCount.size();
        if (total == 0) {
            // Если нет предметов (кроме ВКР), то единственное ограничение — по ВКР
            return true;
        }

        int excellent = 0;
        int graded = 0;

        for (AssessmentKey k : keysToCount) {
            AssessmentAttempt a = latest.get(k);
            if (a == null) {
                continue; // ещё не выставлено
            }
            graded++;
            if (a.grade().isSatisfactoryOrWorse()) {
                return false; // красный диплом невозможен при наличии итогового "удовлетворительно/неуд"
            }
            if (a.grade().isExcellent()) {
                excellent++;
            }
        }

        int missing = total - graded;
        int maxExcellent = excellent + missing;              // максимум "отлично", если всё недостающее станет 5
        int requiredExcellent = (int) Math.ceil(0.75 * total); // нужно минимум 75%

        return maxExcellent >= requiredExcellent;
    }

    public boolean canGetIncreasedScholarship() {
        int sessionSemester = currentSemester - 1;
        if (sessionSemester <= 0) {
            return false;
        }

        // Определяем набор аттестаций, относящихся к этой сессии:
        // 1) если в плане указан семестр — берём из плана
        // 2) иначе — по факту: что уже встречалось в попытках этого семестра
        Set<AssessmentKey> relevant = new LinkedHashSet<>();
        for (PlannedAssessment pa : plan.values()) {
            if (pa.key().type() == AssessmentType.THESIS) continue;
            if (pa.plannedSemester() == sessionSemester) {
                relevant.add(pa.key());
            }
        }
        if (relevant.isEmpty()) {
            for (AssessmentAttempt a : attempts) {
                if (a.semester() == sessionSemester && a.key().type() != AssessmentType.THESIS) {
                    relevant.add(a.key());
                }
            }
        }

        // Если вообще не знаем, какие предметы относятся к прошлой сессии — смысла считать "повышенную" нет
        if (relevant.isEmpty()) {
            return false;
        }

        // Если по любому из предметов в прошлой сессии уже стоит не-отлично (последняя попытка в этом семестре) — нельзя
        for (AssessmentKey k : relevant) {
            AssessmentAttempt lastInSem = latestAttemptInSemester(k, sessionSemester);
            if (lastInSem == null) {
                continue; // оценка ещё не выставлена — считаем, что может стать 5
            }
            if (!lastInSem.grade().isExcellent()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Возвращает набор "ключей предметов" (без ВКР), которые нужно учитывать:
     * если есть план — берём из плана, иначе — из реально встречавшихся попыток.
     */
    private Set<AssessmentKey> plannedOrObservedNonThesisKeys() {
        if (!plan.isEmpty()) {
            Set<AssessmentKey> keys = new LinkedHashSet<>();
            for (PlannedAssessment pa : plan.values()) {
                if (pa.key().type() != AssessmentType.THESIS) {
                    keys.add(pa.key());
                }
            }
            return keys;
        }

        Set<AssessmentKey> keys = new LinkedHashSet<>();
        for (AssessmentAttempt a : attempts) {
            if (a.key().type() != AssessmentType.THESIS) {
                keys.add(a.key());
            }
        }
        return keys;
    }


    private Map<AssessmentKey, AssessmentAttempt> latestAttemptsOverall() {
        Map<AssessmentKey, AssessmentAttempt> latest = new LinkedHashMap<>();
        for (AssessmentAttempt a : attempts) {
            AssessmentAttempt prev = latest.get(a.key());
            if (prev == null || isLater(a, prev)) {
                latest.put(a.key(), a);
            }
        }
        return latest;
    }

    private AssessmentAttempt latestAttemptInSemester(AssessmentKey key, int semester) {
        AssessmentAttempt best = null;
        for (AssessmentAttempt a : attempts) {
            if (!a.key().equals(key)) continue;
            if (a.semester() != semester) continue;
            if (best == null || isLater(a, best)) {
                best = a;
            }
        }
        return best;
    }

    private boolean isLater(AssessmentAttempt a, AssessmentAttempt b) {
        if (a.semester() != b.semester()) {
            return a.semester() > b.semester();
        }
        return a.sequence() > b.sequence();
    }
}
