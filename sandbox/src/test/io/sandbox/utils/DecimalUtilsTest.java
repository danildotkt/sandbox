package io.sandbox.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DecimalUtilsTest {

    @Test
    public void testRoundForDoubleValue_Integer() {
        double value = 10.0;

        String result = DecimalUtils.roundForDoubleValue(value);

        assertEquals("10", result);
    }

    @Test
    public void testRoundForDoubleValue_TwoDecimalPlaces() {
        double value = 10.50;

        String result = DecimalUtils.roundForDoubleValue(value);

        assertEquals("10.5", result);
    }

    @Test
    public void testRoundForDoubleValue_MoreThanTwoDecimalPlaces() {
        double value = 10.1234;

        String result = DecimalUtils.roundForDoubleValue(value);

        assertEquals("10.12", result);
    }
}
