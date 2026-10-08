import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.example.Expression;
import org.example.Variable;
import org.junit.jupiter.api.Test;

class VariableTest {

    @Test
    void variableTest() {
        Expression variable = new Variable("x");

        assertEquals("x", variable.toString());
        assertEquals(10, variable.eval("x = 10; y = 20"));
        assertEquals("1", variable.derivative("x").toString());
        assertEquals("0", variable.derivative("y").toString());
    }

    @Test
    void multiLetterVariableTest() {
        Expression variable = new Variable("temperature");

        assertEquals(
                25,
                variable.eval("x = 10; temperature = 25")
        );

        assertEquals(
                "1",
                variable.derivative("temperature").toString()
        );
    }

    @Test
    void unknownVariableTest() {
        Expression variable = new Variable("x");

        assertThrows(
                IllegalArgumentException.class,
                () -> variable.eval("y = 10")
        );
    }
}
