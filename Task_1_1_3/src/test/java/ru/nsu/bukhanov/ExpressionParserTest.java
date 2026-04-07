package ru.nsu.bukhanov;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExpressionParserTest {

    @Test
    public void testParseSimple() {
        ExpressionParser parser = new ExpressionParser("(3+(2*x))");
        Expression expr = parser.parse();
        assertEquals("(3+(2*x))", expr.print());
    }

    @Test
    public void testParseNested() {
        ExpressionParser parser = new ExpressionParser("((x+y)*(a-b))");
        Expression expr = parser.parse();
        assertEquals("((x+y)*(a-b))", expr.print());
    }

    @Test
    public void testParseVariableName() {
        ExpressionParser parser = new ExpressionParser("(abc+(2*xyz))");
        Expression expr = parser.parse();
        assertEquals("(abc+(2*xyz))", expr.print());
    }
}
