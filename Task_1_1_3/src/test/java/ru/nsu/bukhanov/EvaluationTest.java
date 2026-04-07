package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EvaluationTest {

    @Test
    public void testEvalWithOneVariable() {
        Expression e = new Add(
                new Number(3),
                new Mul(new Number(2), new Variable("x"))
        );
        assertEquals(23.0, e.eval("x = 10"), 1e-9);
    }

    @Test
    public void testEvalWithParser() {
        Expression e = new ExpressionParser("(3+(2*x))").parse();
        assertEquals(23.0, e.eval("x = 10; y = 5"), 1e-9);
    }

    @Test
    public void testUndefinedVariable() {
        Expression e = new Add(new Variable("x"), new Number(1));
        assertThrows(IllegalArgumentException.class, () -> e.eval("y = 2"));
    }
}
