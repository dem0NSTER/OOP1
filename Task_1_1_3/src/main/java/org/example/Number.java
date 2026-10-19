package org.example;

import java.util.Map;

/**
 * Represents an integer constant.
 */
public class Number extends Expression {
    private final int value;

    public Number(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    @Override
    protected int eval(Map<String, Integer> variables) {
        return value;
    }
}
