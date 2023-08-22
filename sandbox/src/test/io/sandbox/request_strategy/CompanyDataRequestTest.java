package io.sandbox.request_strategy;

import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompanyDataRequestTest {

    @Test
    public void testSendRequest() {

        Update update = Mockito.mock(Update.class);
        Map<Long, UserState> hashMap = new HashMap<>();
        TelegramBot telegramBot = Mockito.mock(TelegramBot.class);

        CompanyDataRequest companyDataRequest = new CompanyDataRequest();

        Message message = Mockito.mock(Message.class);
        Mockito.when(update.getMessage()).thenReturn(message);

        companyDataRequest.sendRequest(update, hashMap, telegramBot);

        Mockito.verify(telegramBot).sendMessage(update, "Введите тикер компании \nНапример : Gazp");

        assertEquals(UserState.STATE_COMPANY_DATA_RESPONSE, hashMap.get(message.getChatId()));
    }
}