package io.sandbox.utils;

public class MoneyValueParser {

    public static double parseUnitAndNanoToDouble(long units, int nanos){
        String resultString = units + "." + nanos;
        return Double.parseDouble(resultString);
    }
}
