package io.sandbox.command_request;

import io.sandbox.api_database.JpaServiceClient;
import io.sandbox.telegram_bot.TelegramBotService;
import io.sandbox.user_state.UserState;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

public class StartRequest {

    public static void sendMessage(Update update, HashMap<Long, UserState> stateMap, TelegramBotService telegramBotService){
        telegramBotService.sendMessage(update , switchStateRequest(update, stateMap));
    }

    private static String switchStateRequest(Update update, HashMap<Long, UserState> stateMap){
        long chatId = update.getMessage().getChatId();
        stateMap.remove(chatId);
        if(checkUserExistInDatabase(chatId)){
            stateMap.put(chatId, UserState.STATE_DEFAULT);
            return "Вы уже зарегестрированны в sandbox";
        }
        stateMap.put(chatId, UserState.STATE_START_RESPONSE);
        return "Введите токен sandbox ";

    }

    private static boolean checkUserExistInDatabase(long chatId){
        return JpaServiceClient.isExist(chatId);
    }
}
