package org.example;

public class Number extends Expression {
    private final int value;

    public Number(int value) {
        this.value = value;
    }

    @Override
    public void print() {
        System.out.print(value);
    }

    @Override
    public Expression derivative(String variable) {
        return new Number(0);
    }

    @Override
    public int eval(String variables) {
        return value;
    }
}
