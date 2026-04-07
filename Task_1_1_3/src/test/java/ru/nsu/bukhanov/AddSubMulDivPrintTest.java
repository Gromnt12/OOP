package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AddSubMulDivPrintTest {

    @Test
    public void testAddPrint() {
        Expression e = new Add(new Number(1), new Number(2));
        assertEquals("(1+2)", e.print());
    }

    @Test
    public void testSubPrint() {
        Expression e = new Sub(new Number(4), new Number(3));
        assertEquals("(4-3)", e.print());
    }

    @Test
    public void testMulPrint() {
        Expression e = new Mul(new Number(4), new Variable("x"));
        assertEquals("(4*x)", e.print());
    }

    @Test
    public void testDivPrint() {
        Expression e = new Div(new Variable("x"), new Number(2));
        assertEquals("(x/2)", e.print());
    }
}
