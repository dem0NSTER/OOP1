import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.Expression;
import org.example.ExpressionParser;
import org.junit.jupiter.api.Test;

class ExpressionParserTest {

    private final ExpressionParser parser = new ExpressionParser();

    @Test
    void parseNumberTest() {
        Expression expression = parser.parse("123");

        assertEquals("123", expression.toString());
    }

    @Test
    void parseVariableTest() {
        Expression expression = parser.parse("x");

        assertEquals("x", expression.toString());
    }

    @Test
    void parseAddTest() {
        Expression expression = parser.parse("(2+3)");

        assertEquals("(2+3)", expression.toString());
    }

    @Test
    void parseNestedExpressionTest() {
        Expression expression = parser.parse("(3+(2*x))");

        assertEquals("(3+(2*x))", expression.toString());
        assertEquals(23, expression.eval("x = 10"));
    }

    @Test
    void parseComplexExpressionTest() {
        Expression expression =
                parser.parse("((x+2)*(y-3))");

        assertEquals(
                "((x+2)*(y-3))",
                expression.toString()
        );

        assertEquals(
                28,
                expression.eval("x = 5; y = 7")
        );
    }

    @Test
    void taskExampleTest() {
        Expression expression =
                parser.parse("(3+(2*x))");

        assertEquals(
                "(3+(2*x))",
                expression.toString()
        );

        assertEquals(
                23,
                expression.eval("x = 10; y = 13")
        );

        assertEquals(
                "(0+((0*x)+(2*1)))",
                expression.derivative("x").toString()
        );
    }
}
