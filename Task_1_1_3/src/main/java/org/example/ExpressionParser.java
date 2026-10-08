package org.example;

public class ExpressionParser {

    public Expression parse(String input) {
        input = input.trim();

        if (isNumber(input)) {
            return new Number(Integer.parseInt(input));
        }

        if (!input.startsWith("(")) {
            return new Variable(input);
        }

        String content = input.substring(1, input.length() - 1);
        int operatorIndex = findOperator(content);

        char operator = content.charAt(operatorIndex);

        String leftString = content.substring(0, operatorIndex);
        String rightString = content.substring(operatorIndex + 1);

        Expression left = parse(leftString);
        Expression right = parse(rightString);

        return switch (operator) {
            case '+' -> new Add(left, right);
            case '-' -> new Sub(left, right);
            case '*' -> new Mul(left, right);
            case '/' -> new Div(left, right);
            default -> throw new IllegalArgumentException(
                    "Unknown operator: " + operator
            );
        };

    }

    private boolean isNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private int findOperator(String input) {
        int depth = 0;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '(') {
                depth++;
            } else if (c == ')') {
                depth--;
            } else if (depth == 0 && (c == '+' || c == '-' || c == '*' || c == '/')) {
                return i;
            }
        }

        return -1;
    }

}
