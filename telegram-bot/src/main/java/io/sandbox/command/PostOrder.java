package io.sandbox.command;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.sandbox.grpc_client.CommandClient;
import io.sandbox.telegrambot.TelegramBotService;
import io.sandbox.user_state.UserState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.SQLOutput;
import java.util.HashMap;


@Component
public class PostOrder implements Command{

    @Override
    public void sendTelegramMessageRequest(Update update, HashMap<Long, UserState> hashMap, TelegramBotService telegramBotService) {
        telegramBotService.sendMessage(update, switchStateRequest(update, hashMap));
    }

    @Override
    public void sendTelegramMessageResponse(Update update, HashMap<Long, UserState> hashMap, TelegramBotService telegramBotService) {
        telegramBotService.sendMessage(update, switchStateResponse(update, hashMap));
    }



    public String switchStateRequest(Update update,HashMap<Long, UserState> hashMap){
        var chatId = update.getMessage().getChatId();
        hashMap.remove(chatId);
        hashMap.put(chatId, UserState.STATE_POST_ORDER_RESPONSE);
        return request();
    }

    public String switchStateResponse(Update update,HashMap<Long, UserState> hashMap){
        var chatId = update.getMessage().getChatId();
        try {
            var response =  response(update);
            hashMap.remove(chatId);
            return response;
        } catch (Exception ex){
            System.out.println(ex.getMessage());
            hashMap.put(chatId, UserState.STATE_POST_ORDER_RESPONSE);
            return "попробуйте снова";

        }

    }

    private String request(){
        return "Введите тикер компании и количество лотов \nНапример : \nsber 40";
    }

    private String response(Update update){

        var chatId = update.getMessage().getChatId();

        String inputMessage = update.getMessage().getText();

        String[] inputMessageArr = inputMessage.split(" ", 2);
        String ticker = inputMessageArr[0];


        int quantity = Integer.parseInt(inputMessageArr[1]);

        if(quantity <= 0){
            throw new StatusRuntimeException(Status.INVALID_ARGUMENT);
        }

        var executedOrderPrice = postOrderBuyMarket(chatId, ticker.toUpperCase(), String.valueOf(quantity));

        return "Вы купили " + quantity + " акций " + CommandClient.getInstrument(chatId, ticker) + " по цене " + executedOrderPrice ;
    }

    private double postOrderBuyMarket(long chatId, String ticker, String quantity){
        return CommandClient.postOrderBuyMarket(chatId, ticker, quantity);
    }


}
