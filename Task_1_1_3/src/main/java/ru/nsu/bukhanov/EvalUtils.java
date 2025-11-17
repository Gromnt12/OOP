package ru.nsu.bukhanov;

import java.util.HashMap;
import java.util.Map;

public final class EvalUtils {

    private EvalUtils() {
    }

    public static Map<String, Double> parseAssignments(String assignments) {
        Map<String, Double> result = new HashMap<>();
        if (assignments == null || assignments.isBlank()) {
            return result;
        }
        String[] parts = assignments.split(";");
        for (String part : parts) {
            String trimmed = part.trim();
            if (trimmed.isEmpty()) {
                continue;
            }
            String[] kv = trimmed.split("=");
            if (kv.length != 2) {
                throw new IllegalArgumentException("Bad assignment: " + trimmed);
            }
            String name = kv[0].trim();
            double value = Double.parseDouble(kv[1].trim());
            result.put(name, value);
        }
        return result;
    }
}
