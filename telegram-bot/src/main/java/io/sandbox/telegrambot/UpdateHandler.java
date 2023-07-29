package io.sandbox.telegrambot;


import io.sandbox.user_state.UserState;
import io.sandbox.user_state.UserStateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UpdateHandler {

    private TelegramBot telegramBot;

    @Autowired
    private UserStateManager userStateManager;

    private final HashMap<Long, UserState> map = new HashMap<>();

    public void registerBot(TelegramBot telegramBotService) {
        this.telegramBot = telegramBotService;
    }

    public void updateHandler(Update update) {
        map.putIfAbsent(update.getMessage().getChatId(), UserState.STATE_DEFAULT);
        userStateManager.userStateManager(map, update);

    }

}
