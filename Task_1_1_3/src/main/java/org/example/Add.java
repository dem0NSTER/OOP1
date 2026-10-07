package org.example;

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
    public int eval(String variables) {
        return left.eval(variables) + right.eval(variables);
    }

    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print("+");
        right.print();
        System.out.print(")");
    }
}
