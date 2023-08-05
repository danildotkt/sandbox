package io.sandbox.response;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.sandbox.grpc_client.CommandClient;
import io.sandbox.telegrambot.TelegramBotService;
import io.sandbox.user_state.UserState;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

public class PostOrderResponse {

    public static void sendTelegramMessageResponse(Update update, HashMap<Long, UserState> hashMap, TelegramBotService telegramBotService) {
        telegramBotService.sendMessage(update, switchStateResponse(update, hashMap));
    }

    private static String switchStateResponse(Update update,HashMap<Long, UserState> hashMap){
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

    private static String outputResponse(Update update){

        var chatId = update.getMessage().getChatId();

        String inputMessage = update.getMessage().getText();

        String[] inputMessageArr = inputMessage.split(" ", 2);
        String ticker = inputMessageArr[0];


        int quantity = Integer.parseInt(inputMessageArr[1]);

        if(quantity <= 0){
            throw new StatusRuntimeException(Status.INVALID_ARGUMENT);
        }

        var executedOrderPrice = CommandClient.postOrderBuyMarket(chatId, ticker.toUpperCase(), String.valueOf(quantity));

        return "Вы купили " + quantity + " акции " +
                CommandClient.getInstrumentName(chatId, ticker)
                + " по цене " + executedOrderPrice + " ₽";
    }
}
