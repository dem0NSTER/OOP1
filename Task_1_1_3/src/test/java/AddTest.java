import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.Add;
import org.example.Expression;
import org.example.Number;
import org.example.Variable;
import org.junit.jupiter.api.Test;

class AddTest {

    @Test
    void evalTest() {
        Expression expression = new Add(
                new Number(3),
                new Number(5)
        );

        assertEquals(8, expression.eval(""));
        assertEquals("(3+5)", expression.toString());
    }

    @Test
    void derivativeTest() {
        Expression expression = new Add(
                new Variable("x"),
                new Number(5)
        );

        assertEquals(
                "(1+0)",
                expression.derivative("x").toString()
        );
    }
}
