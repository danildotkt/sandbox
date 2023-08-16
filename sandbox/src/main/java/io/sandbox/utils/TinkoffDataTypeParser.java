package io.sandbox.utils;

import ru.tinkoff.piapi.contract.v1.MoneyValue;
import ru.tinkoff.piapi.contract.v1.Quotation;

import java.text.DecimalFormat;

public class TinkoffDataTypeParser {

    public static double MoneyValueToDouble(MoneyValue moneyValue) {
        return Double.parseDouble(moneyValue.getUnits() + "." + moneyValue.getNano());
    }

    public static double QuotationToDouble(Quotation quotation) {
        return Double.parseDouble(quotation.getUnits() + "." + quotation.getNano());
    }
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
    public static String operationStateParser(String state){
        return switch (state) {
            case "OPERATION_STATE_EXECUTED" -> "Выполнена";
            case "OPERATION_STATE_CANCELED" -> "Отменена";
            case "OPERATION_STATE_PROGRESS" -> "Исполняется";
            default -> "Статус операции не определён";
        };
    }
}
