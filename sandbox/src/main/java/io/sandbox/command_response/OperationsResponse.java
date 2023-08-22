package io.sandbox.command_response;

import io.sandbox.api_tinkoff_invest.InvestApi;
import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import io.sandbox.utils.TinkoffDataTypeParser;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.piapi.contract.v1.Instrument;
import ru.tinkoff.piapi.contract.v1.Operation;

import java.util.List;
import java.util.Map;

public class OperationsResponse implements ResponseStrategy {

    private final InvestApi investApi;

    public OperationsResponse(InvestApi investApi) {
        this.investApi = investApi;
    }

    public void sendResponse(Update update, Map<Long, UserState> hashMap, TelegramBot telegramBot) {

        var operationList = switchStateResponse(update, hashMap);
        var chatId = update.getMessage().getChatId();

        for (Operation operation : operationList) {

            String totalSum = String.valueOf(operation.getPayment().getUnits());
            totalSum = totalSum.substring(1);

            var figi = operation.getFigi();

            Instrument instrument = investApi.getInstrumentByFigi(chatId, figi);

            String info = instrument.getName()
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
            telegramBot.sendMessage(update, info);
        }
    }

    private List<Operation> switchStateResponse(Update update, Map<Long, UserState> hashMap){
        var chatId = update.getMessage().getChatId();
        hashMap.remove(chatId);
        return getOperationsList(update);
    }

    private List<Operation> getOperationsList(Update update){
        return investApi.sandboxOperations(update.getMessage().getChatId());
    }
}
