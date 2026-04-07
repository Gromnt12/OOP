package ru.nsu.bukhanov;

import java.util.Map;
import java.util.Objects;

public class Variable extends Expression {

    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public double eval(Map<String, Double> variables) {
        if (!variables.containsKey(name)) {
            throw new IllegalArgumentException("Variable " + name + " is not defined");
        }
        return variables.get(name);
    }

    @Override
    public Expression derivative(String varName) {
        if (this.name.equals(varName)) {
            return new Number(1.0);
        }
        return new Number(0.0);
    }

    @Override
    public String print() {
        return name;
    }

    @Override
    public Expression simplify() {
        return this;
    }

    public String getName() {
        return name;
    }

    // чтобы сравнивать выражения
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Variable)) return false;
        Variable variable = (Variable) o;
        return name.equals(variable.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
