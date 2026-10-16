package org.example;

import java.util.Map;

/**
 * Represents the sum of two expressions.
 */
public class Add extends Expression {

    private final Expression left;
    private final Expression right;

    public Add(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(
                left.derivative(variable),
                right.derivative(variable)
        );
    }

    @Override
    protected int eval(Map<String, Integer> variables) {
        return left.eval(variables) + right.eval(variables);
    }

    @Override
    public String toString() {
        return "(" + left + "+" + right + ")";
    }
}
