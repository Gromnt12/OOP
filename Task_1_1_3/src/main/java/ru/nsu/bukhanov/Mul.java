package ru.nsu.bukhanov;

import java.util.Map;

public class Mul extends BinaryOperation {

    public Mul(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double eval(Map<String, Double> variables) {
        return left.eval(variables) * right.eval(variables);
    }

    @Override
    public Expression derivative(String varName) {
        // (f * g)' = f' * g + f * g'
        return new Add(
                new Mul(left.derivative(varName), right),
                new Mul(left, right.derivative(varName))
        );
    }

    @Override
    public String print() {
        return "(" + left.print() + "*" + right.print() + ")";
    }

    @Override
    public Expression simplify() {
        Expression ls = left.simplify();
        Expression rs = right.simplify();

        // умножение на 0
        if (ls instanceof Number && ((Number) ls).getValue() == 0.0) {
            return new Number(0.0);
        }
        if (rs instanceof Number && ((Number) rs).getValue() == 0.0) {
            return new Number(0.0);
        }

        // умножение на 1
        if (ls instanceof Number && ((Number) ls).getValue() == 1.0) {
            return rs;
        }
        if (rs instanceof Number && ((Number) rs).getValue() == 1.0) {
            return ls;
        }

        // обе константы
        if (ls instanceof Number && rs instanceof Number) {
            double prod = ((Number) ls).getValue() * ((Number) rs).getValue();
            return new Number(prod);
        }

        // 3 * (4 * x) -> (12 * x)
        if (ls instanceof Number && rs instanceof Mul) {
            Mul rMul = (Mul) rs;
            if (rMul.left instanceof Number) {
                double prod = ((Number) ls).getValue() * ((Number) rMul.left).getValue();
                return new Mul(new Number(prod), rMul.right).simplify();
            }
        }
        // (4 * x) * 3 -> (12 * x)
        if (rs instanceof Number && ls instanceof Mul) {
            Mul lMul = (Mul) ls;
            if (lMul.left instanceof Number) {
                double prod = ((Number) rs).getValue() * ((Number) lMul.left).getValue();
                return new Mul(new Number(prod), lMul.right).simplify();
            }
        }

        return new Mul(ls, rs);
    }
}
