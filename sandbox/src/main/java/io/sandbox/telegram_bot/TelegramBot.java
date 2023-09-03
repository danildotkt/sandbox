package io.sandbox.telegram_bot;


import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Log4j
@Service
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    private final TelegramUpdateHandler telegramUpdateHandler;

    public TelegramBot(TelegramUpdateHandler telegramUpdateHandlerService) {
        this.telegramUpdateHandler = telegramUpdateHandlerService;
    }

    @PostConstruct
    public void init(){
        telegramUpdateHandler.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        var text = update.getMessage().getText();
        var userName = update.getMessage().getChat().getUserName();
        log.info(userName + " : " + text);
        telegramUpdateHandler.updateHandle(update);
    }

    public void sendMessage(Update update, String text) {
        var chatId = update.getMessage().getChatId();
        var message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

}