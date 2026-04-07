package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimplifyTest {

    @Test
    public void testSimplifyDerivative() {
        Expression e = new Add(
                new Number(3),
                new Mul(new Number(2), new Variable("x"))
        );
        Expression de = e.derivative("x");
        Expression simplified = de.simplify();
        assertEquals("2", simplified.print());
    }

    @Test
    public void testSimplifyMulZero() {
        Expression e = new Mul(new Number(0), new Variable("x"));
        assertEquals("0", e.simplify().print());
    }

    @Test
    public void testSimplifySubSame() {
        Expression v = new Variable("x");
        Expression e = new Sub(v, v);
        assertEquals("0", e.simplify().print());
    }
}
