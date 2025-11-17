package ru.nsu.bukhanov;

public class ExpressionParser {

    private final String input;
    private int pos = 0;

    public ExpressionParser(String input) {
        // убираем пробелы
        this.input = input.replaceAll("\\s+", "");
    }

    public Expression parse() {
        Expression expr = parseExpression();
        if (pos != input.length()) {
            throw new IllegalArgumentException("Unexpected tail: " + input.substring(pos));
        }
        return expr;
    }

    private Expression parseExpression() {
        if (pos >= input.length()) {
            throw new IllegalArgumentException("Unexpected end of input");
        }
        char c = input.charAt(pos);
        if (c == '(') {
            pos++; // skip '('
            Expression left = parseExpression();
            if (pos >= input.length()) {
                throw new IllegalArgumentException("Operator expected at position " + pos);
            }
            char op = input.charAt(pos++);
            Expression right = parseExpression();
            if (pos >= input.length() || input.charAt(pos) != ')') {
                throw new IllegalArgumentException("')' expected at position " + pos);
            }
            pos++; // skip ')'
            switch (op) {
                case '+':
                    return new Add(left, right);
                case '-':
                    return new Sub(left, right);
                case '*':
                    return new Mul(left, right);
                case '/':
                    return new Div(left, right);
                default:
                    throw new IllegalArgumentException("Unknown operator: " + op);
            }
        } else if (Character.isDigit(c)) {
            return parseNumber();
        } else {
            return parseVariable();
        }
    }

    private Number parseNumber() {
        int start = pos;
        boolean dotSeen = false;
        while (pos < input.length()) {
            char ch = input.charAt(pos);
            if (Character.isDigit(ch)) {
                pos++;
            } else if (ch == '.' && !dotSeen) {
                dotSeen = true;
                pos++;
            } else {
                break;
            }
        }
        double value = Double.parseDouble(input.substring(start, pos));
        return new Number(value);
    }

    private Variable parseVariable() {
        int start = pos;
        while (pos < input.length() && Character.isLetterOrDigit(input.charAt(pos))) {
            pos++;
        }
        String name = input.substring(start, pos);
        return new Variable(name);
    }
}
