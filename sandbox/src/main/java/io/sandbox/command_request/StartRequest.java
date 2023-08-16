package io.sandbox.command_request;

import io.sandbox.api_database.JpaServiceClient;
import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.ConcurrentHashMap;

public class StartRequest {

    public static void sendMessage(Update update, ConcurrentHashMap<Long, UserState> stateMap, TelegramBot telegramBot){
        telegramBot.sendMessage(update , switchStateRequest(update, stateMap));
    }

    private static String switchStateRequest(Update update, ConcurrentHashMap<Long, UserState> stateMap){
        long chatId = update.getMessage().getChatId();
        stateMap.remove(chatId);
        if(checkUserExistInDatabase(chatId)){
            stateMap.put(chatId, UserState.STATE_DEFAULT);
            return "Вы уже зарегестрированны в sandbox";
        }
        stateMap.put(chatId, UserState.STATE_START_RESPONSE);
        return "Добро пожаловать в песочницу для покупки акций." +
                " Для того что продолжить введите токен в tinkoff invest API https://www.tinkoff.ru/invest/settings/api/" +
                "!ВСТАВЬТЕ ТОКЕН ДЛЯ ПЕСОЧНИЦЫ!";

    }

    private static boolean checkUserExistInDatabase(long chatId){
        return JpaServiceClient.isExist(chatId);
    }
}
