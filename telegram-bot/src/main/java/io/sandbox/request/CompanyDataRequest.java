package io.sandbox.request;

import io.sandbox.telegrambot.TelegramBotService;
import io.sandbox.user_state.UserState;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

public class CompanyDataRequest {
    public static void sendTelegramMessageRequest(Update update, HashMap<Long, UserState> hashMap, TelegramBotService telegramBotService) {
        telegramBotService.sendMessage(update, switchStateRequest(update, hashMap));
    }

    public static String switchStateRequest(Update update, HashMap<Long, UserState> hashMap){
        hashMap.remove(update.getMessage().getChatId());
        hashMap.put(update.getMessage().getChatId(), UserState.STATE_COMPANY_DATA_RESPONSE);
        return "Введите тикер компании ";
    }
}
