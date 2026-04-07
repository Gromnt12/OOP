package ru.nsu.bukhanov;

import java.util.Map;

public class Div extends BinaryOperation {

    public Div(Expression left, Expression right) {
        super(left, right);
    }

    @Override
    public double eval(Map<String, Double> variables) {
        return left.eval(variables) / right.eval(variables);
    }

    @Override
    public Expression derivative(String varName) {
        // (f / g)' = (f' * g - f * g') / (g * g)
        Expression fPrime = left.derivative(varName);
        Expression gPrime = right.derivative(varName);
        Expression numerator = new Sub(
                new Mul(fPrime, right),
                new Mul(left, gPrime)
        );
        Expression denominator = new Mul(right, right);
        return new Div(numerator, denominator);
    }

    @Override
    public String print() {
        return "(" + left.print() + "/" + right.print() + ")";
    }

    @Override
    public Expression simplify() {
        Expression ls = left.simplify();
        Expression rs = right.simplify();

        // деление двух чисел
        if (ls instanceof Number && rs instanceof Number) {
            double v1 = ((Number) ls).getValue();
            double v2 = ((Number) rs).getValue();
            return new Number(v1 / v2);
        }

        // деление на 1
        if (rs instanceof Number && ((Number) rs).getValue() == 1.0) {
            return ls;
        }

        // (k * expr) / k -> expr  (и похожие)
        if (ls instanceof Mul && rs instanceof Number) {
            Mul lMul = (Mul) ls;
            if (lMul.getLeft() instanceof Number) {
                double num = ((Number) lMul.getLeft()).getValue();
                double den = ((Number) rs).getValue();
                if (den != 0.0) {
                    double div = num / den;
                    if (div == 1.0) {
                        return lMul.getRight().simplify();
                    } else {
                        return new Mul(new Number(div), lMul.getRight()).simplify();
                    }
                }
            }
        }

        return new Div(ls, rs);
    }
}
