package io.sandbox.command_request;

import io.sandbox.api_database.JpaServiceClient;
import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

public class StartRequest implements RequestStrategy {

    private final JpaServiceClient jpaServiceClient;

    public StartRequest(JpaServiceClient jpaServiceClient) {
        this.jpaServiceClient = jpaServiceClient;
    }

    public void sendRequest(Update update, Map<Long, UserState> stateMap, TelegramBot telegramBot){
        telegramBot.sendMessage(update , switchStateRequest(update, stateMap));
    }

    private String switchStateRequest(Update update, Map<Long, UserState> stateMap){
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

    private boolean checkUserExistInDatabase(long chatId){
        return jpaServiceClient.isExist(chatId);
    }
}
