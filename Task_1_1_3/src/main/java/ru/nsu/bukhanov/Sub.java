package ru.nsu.bukhanov;

import java.util.Map;

public class Sub extends BinaryOperation {

    public Sub(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double eval(Map<String, Double> variables) {
        return left.eval(variables) - right.eval(variables);
    }

    @Override
    public Expression derivative(String varName) {
        return new Sub(left.derivative(varName), right.derivative(varName));
    }

    @Override
    public String print() {
        return "(" + left.print() + "-" + right.print() + ")";
    }

    @Override
    public Expression simplify() {
        Expression ls = left.simplify();
        Expression rs = right.simplify();
        // a - a -> 0
        if (ls.equals(rs)) {
            return new Number(0.0);
        }
        // обе константы
        if (ls instanceof Number && rs instanceof Number) {
            double diff = ((Number) ls).getValue() - ((Number) rs).getValue();
            return new Number(diff);
        }
        return new Sub(ls, rs);
    }
}
