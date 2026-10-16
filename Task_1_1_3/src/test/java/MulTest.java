import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.Expression;
import org.example.Mul;
import org.example.Number;
import org.example.Variable;
import org.junit.jupiter.api.Test;

class MulTest {

    @Test
    void evalTest() {
        Expression expression = new Mul(
                new Number(4),
                new Number(5)
        );

        assertEquals(20, expression.eval(""));
        assertEquals("(4*5)", expression.toString());
    }

    @Test
    void derivativeTest() {
        Expression expression = new Mul(
                new Variable("x"),
                new Variable("x")
        );

        assertEquals(
                "((1*x)+(x*1))",
                expression.derivative("x").toString()
        );
    }
}
