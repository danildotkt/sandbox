package io.sandbox.telegrambot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;


@Service
public class TelegramBotService extends TelegramLongPollingBot {

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    private final UpdateHandler updateHandler;

    public TelegramBotService(UpdateHandler updateHandlerService) {
        this.updateHandler = updateHandlerService;
    }

    @PostConstruct
    public void init(){
        updateHandler.registerBot(this);
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
        updateHandler.updateHandler(update);
    }

    public void sendMessage(Update update, String text) {
        var chatId = update.getMessage().getChatId();
        var message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            System.out.println("a");// TODO LOGG
        }
    }

}















