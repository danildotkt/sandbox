package io.sandbox.utils;

import io.sandbox.telegram_bot.TelegramBotService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MessageUtilsTest {

    @Mock
    private TelegramBotService telegramBotService;

    @Test
    public void defaultMessage_ShouldSendMessage() {
        Update update = new Update();
        MessageUtils.defaultMessage(update, this.telegramBotService);
        String expectedText =  "Для взаимодействия со мной вы можете " +
                "использовать следущие команды :\n" +
                "/portfolio - просмотр портфеля \n" +
                "/post_order - выставления заявки \n" +
                "/operations - список операций \n" +
                "/company_data - информация компании";
        ((TelegramBotService) Mockito.verify(this.telegramBotService))
                .sendMessage((Update)Mockito.eq(update), (String)Mockito.eq(expectedText));
    }
}