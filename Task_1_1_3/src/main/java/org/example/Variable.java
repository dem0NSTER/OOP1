package org.example;

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
    public int eval(String variables) {
        String[] assignments = variables.split(";");

        for (String assignment : assignments) {
            String[] parts = assignment.split("=");

            String variableName = parts[0].trim();
            int value = Integer.parseInt(parts[1].trim());

            if (name.equals(variableName)) {
                return value;
            }
        }

        throw new IllegalArgumentException("Variable not found: " + name);
    }

    @Override
    public String toString() {
        return name;
    }
}
