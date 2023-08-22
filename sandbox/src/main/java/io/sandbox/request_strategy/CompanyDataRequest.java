package io.sandbox.request_strategy;

import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import io.sandbox.utils.message.CompanyDataMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

public class CompanyDataRequest implements RequestStrategy {

    public void sendRequest(Update update, Map<Long, UserState> hashMap, TelegramBot telegramBot) {
        telegramBot.sendMessage(update, switchStateRequest(update, hashMap));
    }

    private String switchStateRequest(Update update, Map<Long, UserState> hashMap){
        hashMap.remove(update.getMessage().getChatId());
        hashMap.put(update.getMessage().getChatId(), UserState.STATE_COMPANY_DATA_RESPONSE);
        return CompanyDataMessage.tickerPrompt();
    }
}
