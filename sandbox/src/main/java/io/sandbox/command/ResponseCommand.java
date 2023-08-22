package io.sandbox.command;

import io.sandbox.response_strategy.ResponseStrategy;
import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

public class ResponseCommand implements Command {
    private final ResponseStrategy strategy;

    public ResponseCommand(ResponseStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void execute(Update update, Map<Long, UserState> userMap, TelegramBot telegramBot) {
        strategy.sendResponse(update, userMap, telegramBot);
    }
}
