package org.example;

public class Sub extends Expression {
    private final Expression left;
    private final Expression right;

    public Sub(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Expression derivative(String variable) {
        return new Sub(
                left.derivative(variable),
                right.derivative(variable)
        );
    }

    @Override
    public int eval(String variables) {
        return left.eval(variables) - right.eval(variables);
    }

    @Override
    public void print() {
        System.out.print("(");
        left.print();
        System.out.print("-");
        right.print();
        System.out.print(")");
    }
}
