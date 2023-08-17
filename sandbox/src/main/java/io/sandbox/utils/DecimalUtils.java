package io.sandbox.utils;

import java.text.DecimalFormat;

public class DecimalUtils {

    public static String roundForDoubleValue(double value) {
        DecimalFormat df = new DecimalFormat("0.00");
        String roundedNumber = df.format(value);

        if (value % 1 == 0) {
            return String.format("%.0f", value);
        } else {
            if (roundedNumber.endsWith("00")) {
                return roundedNumber.substring(0, roundedNumber.indexOf('.'));
            } else if (roundedNumber.endsWith("0")) {
                return roundedNumber.substring(0, roundedNumber.length() - 1);
            } else {
                return roundedNumber;
            }
        }
    }
}
