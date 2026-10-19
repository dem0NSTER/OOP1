package org.example;

import java.util.Map;

/**
 * Represents a named variable in an expression.
 */
public class Variable extends Expression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public Expression derivative(String variable) {
        if (name.equals(variable)) {
            return new Number(1);
        }

        return new Number(0);
    }

    @Override
    protected int eval(Map<String, Integer> variables) {
        Integer value = variables.get(name);
        if (value == null) {
            throw new IllegalArgumentException("Variable not found: " + name);
        }

        return value;
    }

    @Override
    public String toString() {
        return name;
    }
}
