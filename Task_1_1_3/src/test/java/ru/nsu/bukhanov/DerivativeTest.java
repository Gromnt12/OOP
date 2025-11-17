package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DerivativeTest {

    @Test
    public void testDerivativeOfExample() {
        Expression e = new Add(
                new Number(3),
                new Mul(new Number(2), new Variable("x"))
        );
        Expression de = e.derivative("x");
        assertEquals("(0+((0*x)+(2*1)))", de.print());
    }

    @Test
    public void testDerivativeOfDiv() {
        Expression e = new Div(new Variable("x"), new Variable("x"));
        Expression de = e.derivative("x");
        // просто проверим, что парсится и что это выражение можно напечатать
        assertNotNull(de.print());
    }
}
