package io.sandbox.request_strategy;

import io.sandbox.api_database.JpaService;
import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import io.sandbox.utils.message.StartMessage;
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
            return StartMessage.alreadyRegisteredMessage();
        }
        stateMap.put(chatId, UserState.STATE_START_RESPONSE);
        return StartMessage.welcomeMessage();
    }

    private boolean checkUserExistInDatabase(long chatId){
        return jpaService.isExist(chatId);
    }
}
