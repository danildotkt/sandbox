package io.sandbox.response;

import io.sandbox.entity.TelegramUser;
import io.sandbox.grpc_client.CommandClient;
import io.sandbox.kafka.TelegramUserProducer;
import io.sandbox.telegrambot.TelegramBotService;
import io.sandbox.user_state.UserState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

public class StartResponse {

    public static void sendTelegramMessageResponse(Update update, HashMap<Long, UserState> stateMap, TelegramBotService telegramBotService){
        telegramBotService.sendMessage(update, switchStateResponse(update, stateMap));
    }

    private static String switchStateResponse(Update update, HashMap<Long, UserState> stateMap){
        long chatId = update.getMessage().getChatId();
        var response = createNewSandbox(update);
        stateMap.remove(chatId);
        if(response.equals("Попробуйте ввести токен снова")){
            stateMap.put(chatId, UserState.STATE_START_RESPONSE);
            return response;
        }
        stateMap.put(chatId, UserState.STATE_DEFAULT);
        return response;

    }

    private static String createNewSandbox(Update update){

        var chatId = update.getMessage().getChatId();
        var inputMessage = update.getMessage().getText();
        String accountId = null;

        try {
            accountId = CommandClient.createNewSandbox(inputMessage);
        }catch (Exception e ){
            System.out.println(e.getMessage());
            return "Попробуйте ввести токен снова";
        }

        TelegramUser newTelegramUser = TelegramUser
                .builder()
                .chatId(chatId)
                .sandboxToken(inputMessage)
                .sandboxId(accountId)
                .build();

        saveTelegramUser(newTelegramUser);

        return "Ваш sandbox счет успешно создан и на него зачисленно 6 миллионов рублей";
    }

    private static void saveTelegramUser(TelegramUser user){
        var telegramUserProducer = new TelegramUserProducer();
        telegramUserProducer.sendTelegramUser(user);
    }
}
