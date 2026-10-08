import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.Expression;
import org.example.Number;
import org.example.Sub;
import org.example.Variable;
import org.junit.jupiter.api.Test;

class SubTest {

    @Test
    void evalTest() {
        Expression expression = new Sub(
                new Number(10),
                new Number(3)
        );

        assertEquals(7, expression.eval(""));
        assertEquals("(10-3)", expression.toString());
    }

    @Test
    void derivativeTest() {
        Expression expression = new Sub(
                new Variable("x"),
                new Variable("y")
        );

        assertEquals(
                "(1-0)",
                expression.derivative("x").toString()
        );
    }
}
