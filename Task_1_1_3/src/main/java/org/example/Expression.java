package org.example;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines an arithmetic expression that can be evaluated and differentiated.
 */
public abstract class Expression {

    public abstract Expression derivative(String variable);

    /**
     * Evaluates this expression after parsing the variable assignments once.
     *
     * @param variables semicolon-separated assignments, or an empty string
     * @return the value of this expression
     */
    public int eval(String variables) {
        Map<String, Integer> values = new HashMap<>();

        if (!variables.isBlank()) {
            for (String assignment : variables.split(";")) {
                String[] parts = assignment.split("=");

                String variable = parts[0].trim();
                int value = Integer.parseInt(parts[1].trim());

                values.put(variable, value);
            }
        }

        return eval(values);
    }

    /**
     * Evaluates this expression using the parsed variable assignments.
     *
     * @param variables the variable values shared by all child expressions
     * @return the value of this expression
     */
    protected abstract int eval(Map<String, Integer> variables);

    @Override
    public abstract String toString();

    public void print() {
        System.out.print(this);
    }
}
