package io.sandbox.utils;

import ru.tinkoff.piapi.contract.v1.MoneyValue;
import ru.tinkoff.piapi.contract.v1.Quotation;

public class TinkoffDataTypeParser {

    public static double MoneyValueToDouble(MoneyValue moneyValue) {
        var notRoundValue =  Double.parseDouble(moneyValue.getUnits() + "." + moneyValue.getNano());
        return Double.parseDouble(DecimalUtils.roundForDoubleValue(notRoundValue));
    }

    public static double QuotationToDouble(Quotation quotation) {
        var notRoundValue =  Double.parseDouble(quotation.getUnits() + "." + quotation.getNano());
        return Double.parseDouble(DecimalUtils.roundForDoubleValue(notRoundValue));
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
