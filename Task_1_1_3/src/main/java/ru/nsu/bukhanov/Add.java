package ru.nsu.bukhanov;

import java.util.Map;

public class Add extends BinaryOperation {

    public Add(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double eval(Map<String, Double> variables) {
        return left.eval(variables) + right.eval(variables);
    }

    @Override
    public Expression derivative(String varName) {
        return new Add(left.derivative(varName), right.derivative(varName));
    }

    @Override
    public String print() {
        return "(" + left.print() + "+" + right.print() + ")";
    }

    @Override
    public Expression simplify() {
        Expression ls = left.simplify();
        Expression rs = right.simplify();

        // обе константы
        if (ls instanceof Number && rs instanceof Number) {
            double sum = ((Number) ls).getValue() + ((Number) rs).getValue();
            return new Number(sum);
        }

        // 0 + x -> x
        if (ls instanceof Number && ((Number) ls).getValue() == 0.0) {
            return rs;
        }
        // x + 0 -> x
        if (rs instanceof Number && ((Number) rs).getValue() == 0.0) {
            return ls;
        }

        // x + x -> 2 * x
        if (ls.equals(rs)) {
            return new Mul(new Number(2.0), ls);
        }

        // 3 + (3 + expr) -> (6 + expr)
        if (ls instanceof Number && rs instanceof Add) {
            Add rAdd = (Add) rs;
            if (rAdd.left instanceof Number) {
                double sum = ((Number) ls).getValue() + ((Number) rAdd.left).getValue();
                return new Add(new Number(sum), rAdd.right).simplify();
            }
        }
        // (3 + expr) + 3 -> (6 + expr)
        if (rs instanceof Number && ls instanceof Add) {
            Add lAdd = (Add) ls;
            if (lAdd.left instanceof Number) {
                double sum = ((Number) rs).getValue() + ((Number) lAdd.left).getValue();
                return new Add(new Number(sum), lAdd.right).simplify();
            }
        }

        return new Add(ls, rs);
    }
}
