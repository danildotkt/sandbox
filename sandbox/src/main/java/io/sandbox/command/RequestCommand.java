package io.sandbox.command;

import io.sandbox.request_strategy.RequestStrategy;
import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

public class RequestCommand implements Command {
    private final RequestStrategy strategy;

    public RequestCommand(RequestStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void execute(Update update, Map<Long, UserState> userMap, TelegramBot telegramBot) {
        strategy.sendRequest(update, userMap, telegramBot);
    }
}
