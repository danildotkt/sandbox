package io.sandbox.utils.parser;

import io.sandbox.utils.parser.DecimalParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DecimalParserTest {

    @Test
    public void testRoundForDoubleValue_Integer() {
        double value = 10.0;

        String result = DecimalParser.roundForDoubleValue(value);

        assertEquals("10", result);
    }

    @Test
    public void testRoundForDoubleValue_TwoDecimalPlaces() {
        double value = 10.50;

        String result = DecimalParser.roundForDoubleValue(value);

        assertEquals("10.5", result);
    }

    @Test
    public void testRoundForDoubleValue_MoreThanTwoDecimalPlaces() {
        double value = 10.1234;

        String result = DecimalParser.roundForDoubleValue(value);

        assertEquals("10.12", result);
    }
}
