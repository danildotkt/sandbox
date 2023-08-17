package io.sandbox.telegram_bot;

import io.sandbox.user_state.UserState;
import io.sandbox.user_state.UserStateSwitcher;
import io.sandbox.utils.MessageUtils;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Service
public class TelegramUpdateHandler {

    private TelegramBot telegramBot;

    private final UserStateSwitcher userStateSwitcher;

    private final Map<Long, UserState> map = new HashMap<>();

    public TelegramUpdateHandler(UserStateSwitcher userStateSwitcher) {
        this.userStateSwitcher = userStateSwitcher;
    }

    public void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void updateHandle(Update update) {
        if(update.getMessage().hasText()) {
            handleTextMessage(update);
        }
        else{
            handleUnexpectedMessage(update);
        }
    }

    private void handleTextMessage(Update update){
        map.putIfAbsent(update.getMessage().getChatId(), UserState.STATE_DEFAULT);
        userStateSwitcher.userStateSwitch(map, update, telegramBot);
    }
    private void handleUnexpectedMessage(Update update){
        MessageUtils.defaultMessage(update, telegramBot);
    }

}

