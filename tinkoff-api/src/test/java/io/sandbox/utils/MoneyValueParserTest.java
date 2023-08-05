package io.sandbox.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyValueParserTest {

    @Test
    public void testParseUnitsNanosToDouble() {
        double expected = 14.88;
        double actual = MoneyValueParser.parseUnitAndNanoToDouble(14, 88);
        assertEquals(expected, actual, 0.001);
    }
}
