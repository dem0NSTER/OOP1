import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.example.Div;
import org.example.Expression;
import org.example.Number;
import org.example.Variable;
import org.junit.jupiter.api.Test;

class DivTest {

    @Test
    void evalTest() {
        Expression expression = new Div(
                new Number(10),
                new Number(2)
        );

        assertEquals(5, expression.eval(""));
        assertEquals("(10/2)", expression.toString());
    }

    @Test
    void derivativeTest() {
        Expression expression = new Div(
                new Variable("x"),
                new Number(2)
        );

        assertEquals(
                "(((1*2)-(x*0))/(2*2))",
                expression.derivative("x").toString()
        );
    }

    @Test
    void divisionByZeroTest() {
        Expression expression = new Div(
                new Number(10),
                new Number(0)
        );

        assertThrows(
                ArithmeticException.class,
                () -> expression.eval("")
        );
    }
}
