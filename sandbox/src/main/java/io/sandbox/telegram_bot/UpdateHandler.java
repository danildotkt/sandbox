package io.sandbox.telegram_bot;

import io.sandbox.user_state.UserState;
import io.sandbox.user_state.UserStateSwitcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

@Service
public class UpdateHandler {

    private TelegramBotService telegramBotService;

    @Autowired
    private UserStateSwitcher userStateSwitcher;

    private final HashMap<Long, UserState> map = new HashMap<>();

    public void registerBot(TelegramBotService telegramBotService) {
        this.telegramBotService = telegramBotService;
    }

    public void updateHandler(Update update) {

        map.putIfAbsent(update.getMessage().getChatId(), UserState.STATE_DEFAULT);
        userStateSwitcher.userStateSwitch(map, update, telegramBotService);
    }

}

