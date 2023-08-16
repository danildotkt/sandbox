package io.sandbox.command_response;

import io.sandbox.api_tinkoff_invest.TinkoffApi;
import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import io.sandbox.utils.TinkoffDataTypeParser;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.piapi.contract.v1.Instrument;
import ru.tinkoff.piapi.contract.v1.Operation;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class OperationsResponse {
    public static void sendMessage(Update update, ConcurrentHashMap<Long, UserState> hashMap, TelegramBot telegramBot) {

        var operationList = switchStateResponse(update, hashMap);
        var chatId = update.getMessage().getChatId();

        for (Operation operation : operationList) {

            String st = String.valueOf(operation.getPayment().getUnits());
            st = st.substring(1);

            var figi = operation.getFigi();

            Instrument instrument = TinkoffApi.getInstrumentByFigi(chatId, figi);

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
                    + st
                    + " ₽";
            telegramBot.sendMessage(update, info);
        }
    }

    private static List<Operation> switchStateResponse(Update update, ConcurrentHashMap<Long, UserState> hashMap){
        var chatId = update.getMessage().getChatId();
        hashMap.remove(chatId);
        return getOperationsList(update);
    }

    private static List<Operation> getOperationsList(Update update){
        return TinkoffApi.sandboxOperations(update.getMessage().getChatId());
    }
}
