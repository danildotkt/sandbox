package io.sandbox.utils.message;

import io.sandbox.utils.parser.TinkoffDataTypeParser;
import ru.tinkoff.piapi.contract.v1.Instrument;
import ru.tinkoff.piapi.contract.v1.Operation;

public class OperationMessage {

    public static String operationInfoMessage(Operation operation, Instrument instrument) {
        String totalSum = String.valueOf(operation.getPayment().getUnits());
        totalSum = totalSum.substring(1);

        return instrument.getName()
                + "\nСтатус заявки : "
                + TinkoffDataTypeParser.operationStateParser(operation.getState().toString())
                + "\nКоличество акций : "
                + operation.getQuantity()
                + " шт"
                + "\nЦена за акцию : "
                + TinkoffDataTypeParser.MoneyValueToDouble(operation.getPrice())
                + " ₽"
                + "\nОбщая сумма : "
                + totalSum
                + " ₽";
    }
}
