package org.example;

public abstract class Expression {

    public abstract Expression derivative(String variable);

    public abstract int eval(String variables);

    public abstract void print();
}
