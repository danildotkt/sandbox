package io.sandbox.telegram_bot;

import io.sandbox.user_state.UserState;
import io.sandbox.user_state.UserStateSwitcher;
import io.sandbox.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class TelegramUpdateHandler {

    private TelegramBot telegramBot;

    private final UserStateSwitcher userStateSwitcher;

    private final ConcurrentHashMap<Long, UserState> map = new ConcurrentHashMap<>();

    public TelegramUpdateHandler(UserStateSwitcher userStateSwitcher) {
        this.userStateSwitcher = userStateSwitcher;
    }

    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void updateHandle(Update update) {
        if(update.getMessage().hasText()) {
            map.putIfAbsent(update.getMessage().getChatId(), UserState.STATE_DEFAULT);
            userStateSwitcher.userStateSwitch(map, update, telegramBot);
        }
        else{
            MessageUtils.defaultMessage(update, telegramBot);
        }
    }

}

