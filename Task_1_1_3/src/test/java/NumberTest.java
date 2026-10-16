import static org.junit.jupiter.api.Assertions.assertEquals;

import org.example.Expression;
import org.example.Number;
import org.junit.jupiter.api.Test;

class NumberTest {

    @Test
    void numberTest() {
        Expression number = new Number(5);

        assertEquals("5", number.toString());
        assertEquals(5, number.eval("x = 10"));
        assertEquals("0", number.derivative("x").toString());
    }
}
