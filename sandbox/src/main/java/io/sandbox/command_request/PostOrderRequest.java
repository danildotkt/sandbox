package io.sandbox.command_request;

import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

public class PostOrderRequest implements RequestStrategy {

    public void sendRequest(Update update, Map<Long, UserState> hashMap, TelegramBot telegramBot) {
        telegramBot.sendMessage(update, switchStateRequest(update, hashMap));
    }

    private String switchStateRequest(Update update, Map<Long, UserState> hashMap){
        var chatId = update.getMessage().getChatId();
        hashMap.remove(chatId);
        hashMap.put(chatId, UserState.STATE_POST_ORDER_RESPONSE);
        return "Введите тикер компании и количество лотов \nНапример : \nsber 40";
    }

}
