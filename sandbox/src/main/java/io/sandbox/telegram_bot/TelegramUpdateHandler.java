package io.sandbox.telegram_bot;

import io.sandbox.user_state.UserState;
import io.sandbox.utils.message.CommonMessage;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Service
public class TelegramUpdateHandler {

    private TelegramBot telegramBot;

    private final CommandExecutor commandExecutor;

    private final Map<Long, UserState> map = new HashMap<>();

    public TelegramUpdateHandler(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
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
        commandExecutor.executeCommand(map, update, telegramBot);
    }
    private void handleUnexpectedMessage(Update update){
        CommonMessage.sendDefaultMessage(update, telegramBot);
    }

}

