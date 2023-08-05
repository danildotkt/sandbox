package io.sandbox.utils;

import ru.tinkoff.piapi.contract.v1.MoneyValue;
import ru.tinkoff.piapi.contract.v1.Quotation;

public class TinkoffDataTypeParser {

    public static double MoneyValueToDouble(MoneyValue moneyValue) {
        return Double.parseDouble(moneyValue.getUnits() + "." + moneyValue.getNano());
    }

    public static double QuotationToDouble(Quotation quotation) {
        return Double.parseDouble(quotation.getUnits() + "." + quotation.getNano());
    }
}
