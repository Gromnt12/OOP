package ru.nsu.bukhanov;

import java.util.Map;

public abstract class Expression {

    public abstract double eval(Map<String, Double> variables);

    public double eval(String assignments) {
        Map<String, Double> vars = EvalUtils.parseAssignments(assignments);
        return eval(vars);
    }

    public abstract Expression derivative(String varName);

    public abstract String print();

    public void printToConsole() {
        System.out.println(print());
    }

    public Expression simplify() {
        return this;
    }

    @Override
    public String toString() {
        return print();
    }
}
