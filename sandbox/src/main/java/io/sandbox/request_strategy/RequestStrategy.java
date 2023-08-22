package io.sandbox.request_strategy;

import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

public interface RequestStrategy {
    void sendRequest(Update update, Map<Long, UserState> hashMap, TelegramBot telegramBot);
}
