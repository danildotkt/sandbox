package io.sandbox.command_request;

import io.sandbox.api_database.JpaService;
import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

public class StartRequest implements RequestStrategy {

    private final JpaService jpaService;

    public StartRequest(JpaService jpaService) {
        this.jpaService = jpaService;
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
        return jpaService.isExist(chatId);
    }
}
