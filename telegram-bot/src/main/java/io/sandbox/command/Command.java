package io.sandbox.command;

import io.sandbox.telegrambot.TelegramBotService;
import io.sandbox.user_state.UserState;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

public interface Command extends CommandResponse{
    void sendTelegramMessageRequest(Update update, HashMap<Long, UserState> hashMap, TelegramBotService telegramBotService);
    void sendTelegramMessageResponse(Update update, HashMap<Long, UserState> hashMap, TelegramBotService telegramBotService);
}