package org.example;

import java.util.Map;

/**
 * Represents the product of two expressions.
 */
public class Mul extends Expression {
    private final Expression left;
    private final Expression right;

    public Mul(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Expression derivative(String variable) {
        return new Add(
                new Mul(
                        left.derivative(variable),
                        right
                ),
                new Mul(
                        left,
                        right.derivative(variable)
                )
        );
    }

    @Override
    protected int eval(Map<String, Integer> variables) {
        return left.eval(variables) * right.eval(variables);
    }

    @Override
    public String toString() {
        return "(" + left + "*" + right + ")";
    }
}
