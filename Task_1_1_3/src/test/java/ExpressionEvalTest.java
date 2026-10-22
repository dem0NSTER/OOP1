import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.example.Add;
import org.example.Div;
import org.example.Expression;
import org.example.Mul;
import org.example.Number;
import org.example.Sub;
import org.example.Variable;
import org.junit.jupiter.api.Test;

class ExpressionEvalTest {

    @Test
    void nestedOperationsShareParsedVariablesTest() {
        List<Map<String, Integer>> receivedMaps = new ArrayList<>();
        Expression x = recordingVariable("x", receivedMaps);
        Expression y = recordingVariable("y", receivedMaps);
        Expression expression = new Div(
                new Mul(new Add(x, y), new Sub(x, new Number(2))), y
        );

        assertEquals(14, expression.eval("x = 10; y = 13"));
        assertEquals(4, receivedMaps.size());
        assertEquals(Map.of("x", 10, "y", 13), receivedMaps.get(0));
        for (Map<String, Integer> variables : receivedMaps) {
            assertSame(receivedMaps.get(0), variables);
        }
    }

    @Test
    void eachEvaluationUsesCurrentAssignmentsTest() {
        Expression expression = new Add(new Variable("x"), new Variable("y"));

        assertEquals(23, expression.eval("x = 10; y = 13"));
        assertEquals(-1, expression.eval(" x = -3 ; y = 2 "));
        assertThrows(IllegalArgumentException.class, () -> expression.eval("x = 10"));
    }

    @Test
    void emptyAssignmentsTest() {
        assertEquals(5, new Number(5).eval(""));
        assertEquals(5, new Number(5).eval("   "));
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class, () -> new Variable("x").eval("")
        );
        assertEquals("Variable not found: x", exception.getMessage());
    }

    private Expression recordingVariable(String name, List<Map<String, Integer>> receivedMaps) {
        return new Variable(name) {
            @Override
            protected int eval(Map<String, Integer> variables) {
                receivedMaps.add(variables);
                return super.eval(variables);
            }
        };
    }
}
