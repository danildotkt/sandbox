package io.sandbox.command_request;

import io.sandbox.telegram_bot.TelegramBotService;
import io.sandbox.user_state.UserState;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

public class PostOrderRequest {

    public static void sendMessage(Update update, HashMap<Long, UserState> hashMap, TelegramBotService telegramBotService) {
        telegramBotService.sendMessage(update, switchStateRequest(update, hashMap));
    }

    public static String switchStateRequest(Update update, HashMap<Long, UserState> hashMap){
        var chatId = update.getMessage().getChatId();
        hashMap.remove(chatId);
        hashMap.put(chatId, UserState.STATE_POST_ORDER_RESPONSE);
        return "Введите тикер компании и количество лотов \nНапример : \nsber 40";
    }

}
