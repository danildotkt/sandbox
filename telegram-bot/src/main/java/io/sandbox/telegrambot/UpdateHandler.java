package io.sandbox.telegrambot;


import io.sandbox.kafka.UpdateProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UpdateHandler {

    private TelegramBot telegramBot;

    @Autowired
    private UpdateProducer telegramBotProducer;

    public void registerBot(TelegramBot telegramBotService) {
        this.telegramBot = telegramBotService;
    }

    public void updateHandler(Update update) {

        telegramBotProducer.sendUpdate(update);
        telegramBot.sendMessage(update.getMessage().getChatId(), "hi");

    }

}
