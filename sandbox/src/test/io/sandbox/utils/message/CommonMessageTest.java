package io.sandbox.utils.message;

import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.utils.message.CommonMessage;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.mockito.Mockito.verify;


public class CommonMessageTest {

    @Test
    public void defaultMessage_ShouldReturnCorrectMessage() {

        Update update = new Update();
        TelegramBot telegramBot = Mockito.mock(TelegramBot.class);

        CommonMessage.sendDefaultMessage(update, telegramBot);

        String expectedMessage = "Для взаимодействия со мной вы можете использовать следующие команды :\n" +
                "/portfolio - просмотр портфеля \n" +
                "/post_order - покупка акций \n" +
                "/operations - список операций \n" +
                "/company_data - финансовые отчеты компании";

        verify(telegramBot).sendMessage(update, expectedMessage);
    }
}