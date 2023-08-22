package io.sandbox.command;

import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

public interface Command {
    void execute(Update update, Map<Long, UserState> userMap, TelegramBot telegramBot);
}
