package io.sandbox.response_strategy;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.sandbox.api_tinkoff_invest.InvestApi;
import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import io.sandbox.utils.message.PostOrderMessage;
import io.sandbox.utils.parser.DecimalParser;
import io.sandbox.utils.parser.TelegramDataTypeParser;
import io.sandbox.utils.parser.TinkoffDataTypeParser;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

public class PostOrderResponse implements ResponseStrategy {

    private final InvestApi investApi;

    public PostOrderResponse(InvestApi investApi) {
        this.investApi = investApi;
    }


    public void sendResponse(Update update, Map<Long, UserState> hashMap, TelegramBot telegramBot) {
        telegramBot.sendMessage(update, switchStateResponse(update, hashMap));
    }

    private String switchStateResponse(Update update,Map<Long, UserState> hashMap){
        var chatId = update.getMessage().getChatId();

        try {
            var response =  executeOrder(update);
            hashMap.remove(chatId);
            return response;
        } catch (Exception ex){
            hashMap.put(chatId, UserState.STATE_POST_ORDER_RESPONSE);
            return PostOrderMessage.incorrectInputMessage();
        }
    }

    private String executeOrder(Update update) {
        var chatId = update.getMessage().getChatId();
        var ticker = TelegramDataTypeParser.getTickerFromUpdate(update);
        var quantity = TelegramDataTypeParser.getQuantityFromUpdate(update);
        checkQuantityMoreThanZero(quantity);
        return executeOrderAndReturnResponse(chatId, ticker, quantity);
    }

    private String executeOrderAndReturnResponse(Long chatId, String ticker, int quantity) {

        var order = investApi.postOrderBuyMarket(chatId, ticker.toUpperCase(), String.valueOf(quantity));
        var executedOrderPrice = TinkoffDataTypeParser.MoneyValueToDouble(order.getExecutedOrderPrice());
        var instrument = investApi.getInstrumentByTicker(chatId, ticker);
        var executedPricePerStock = DecimalParser.roundForDoubleValue(executedOrderPrice / quantity);

        return PostOrderMessage.formatOrder(quantity,instrument.getName(), executedPricePerStock);
    }

    private void checkQuantityMoreThanZero(int quantity) {
        if (quantity <= 0) {
            throw new StatusRuntimeException(Status.INVALID_ARGUMENT);
        }
    }
}
