package io.sandbox.utils;

import org.junit.jupiter.api.Test;
import ru.tinkoff.piapi.contract.v1.MoneyValue;
import ru.tinkoff.piapi.contract.v1.Quotation;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TinkoffDataTypeParserTest {

    @Test
    public void testMoneyValueToDouble() {
        MoneyValue moneyValue = MoneyValue.newBuilder()
                .setUnits(10)
                .setNano(500000000)
                .build();

        double result = TinkoffDataTypeParser.MoneyValueToDouble(moneyValue);

        assertEquals(10.5, result);
    }

    @Test
    public void testQuotationToDouble() {
        Quotation quotation = Quotation.newBuilder()
                .setUnits(20)
                .setNano(750000000)
                .build();

        double result = TinkoffDataTypeParser.QuotationToDouble(quotation);

        assertEquals(20.75, result);
    }


    @Test
    public void testOperationStateParser_Executed() {
        String state = "OPERATION_STATE_EXECUTED";

        String result = TinkoffDataTypeParser.operationStateParser(state);

        assertEquals("Выполнена", result);
    }

    @Test
    public void testOperationStateParser_Canceled() {
        String state = "OPERATION_STATE_CANCELED";

        String result = TinkoffDataTypeParser.operationStateParser(state);

        assertEquals("Отменена", result);
    }

    @Test
    public void testOperationStateParser_Progress() {
        String state = "OPERATION_STATE_PROGRESS";

        String result = TinkoffDataTypeParser.operationStateParser(state);

        assertEquals("Исполняется", result);
    }

    @Test
    public void testOperationStateParser_Default() {
        String state = "UNKNOWN_STATE";

        String result = TinkoffDataTypeParser.operationStateParser(state);

        assertEquals("Статус операции не определён", result);
    }
}