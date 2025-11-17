package ru.nsu.bukhanov;

import java.util.Objects;

public abstract class BinaryOperation extends Expression {

    protected final Expression left;
    protected final Expression right;

    protected BinaryOperation(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    public Expression getLeft() {
        return left;
    }

    public Expression getRight() {
        return right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BinaryOperation)) return false;
        BinaryOperation that = (BinaryOperation) o;
        return Objects.equals(left, that.left) &&
                Objects.equals(right, that.right) &&
                this.getClass().equals(that.getClass());
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right, getClass());
    }
}
