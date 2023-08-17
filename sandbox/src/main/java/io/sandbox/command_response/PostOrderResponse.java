package io.sandbox.command_response;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.sandbox.api_tinkoff_invest.TinkoffInvestApiClient;
import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import io.sandbox.utils.TinkoffDataTypeParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;


public class PostOrderResponse implements ResponseStrategy {

    private final TinkoffInvestApiClient tinkoffInvestApiClient;

    public PostOrderResponse(TinkoffInvestApiClient tinkoffInvestApiClient) {
        this.tinkoffInvestApiClient = tinkoffInvestApiClient;
    }

    public void sendResponse(Update update, Map<Long, UserState> hashMap, TelegramBot telegramBot) {
        telegramBot.sendMessage(update, switchStateResponse(update, hashMap));
    }

    private String switchStateResponse(Update update,Map<Long, UserState> hashMap){
        var chatId = update.getMessage().getChatId();

        try {
            var response =  outputResponse(update);
            hashMap.remove(chatId);
            return response;
        } catch (Exception ex){
            hashMap.put(chatId, UserState.STATE_POST_ORDER_RESPONSE);
            return "попробуйте снова";
        }
    }

    private String outputResponse(Update update){

        var chatId = update.getMessage().getChatId();

        String inputMessage = update.getMessage().getText();

        String[] inputMessageArr = inputMessage.split(" ", 2);
        String ticker = inputMessageArr[0];


        int quantity = Integer.parseInt(inputMessageArr[1]);

        if(quantity <= 0){
            throw new StatusRuntimeException(Status.INVALID_ARGUMENT);
        }

        var order = tinkoffInvestApiClient.postOrderBuyMarket(chatId, ticker.toUpperCase(), String.valueOf(quantity));
        var executedOrderPrice = TinkoffDataTypeParser.MoneyValueToDouble(order.getExecutedOrderPrice());
        var instrument = tinkoffInvestApiClient.getInstrumentByTicker(chatId, ticker);

        return "Вы купили " + quantity + " акции " +
                instrument.getName()
                + " по цене " + executedOrderPrice/quantity + " ₽";
    }
}
