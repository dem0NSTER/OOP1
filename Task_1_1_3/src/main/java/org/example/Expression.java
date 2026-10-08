package org.example;

/**
 * Defines an arithmetic expression that can be evaluated and differentiated.
 */
public abstract class Expression {

    public abstract Expression derivative(String variable);

    public abstract int eval(String variables);

    @Override
    public abstract String toString();

    public void print() {
        System.out.print(toString());
    }
}
