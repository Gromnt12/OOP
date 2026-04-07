package ru.nsu.bukhanov;

import java.util.Map;
import java.util.Objects;

public class Number extends Expression {

    private final double value;

    public Number(double value) {
        this.value = value;
    }

    @Override
    public double eval(Map<String, Double> variables) {
        return value;
    }

    @Override
    public Expression derivative(String varName) {
        return new Number(0.0);
    }

    @Override
    public String print() {
        // чтобы не печатать 3.0
        if (value == Math.rint(value)) {
            return Integer.toString((int) value);
        }
        return Double.toString(value);
    }

    @Override
    public Expression simplify() {
        return this;
    }

    public double getValue() {
        return value;
    }

    // для сравнений в simplify()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Number)) return false;
        Number number = (Number) o;
        return Double.compare(number.value, value) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
