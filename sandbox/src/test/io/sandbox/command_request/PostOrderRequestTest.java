package io.sandbox.command_request;

import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostOrderRequestTest {

    @Test
    public void testSendRequest() {

        Update update = Mockito.mock(Update.class);
        Map<Long, UserState> hashMap = new HashMap<>();
        TelegramBot telegramBot = Mockito.mock(TelegramBot.class);

        PostOrderRequest postOrderRequest = new PostOrderRequest();

        Message message = Mockito.mock(Message.class);
        Mockito.when(update.getMessage()).thenReturn(message);
        Mockito.when(message.getChatId()).thenReturn(123456L);

        postOrderRequest.sendRequest(update, hashMap, telegramBot);

        Mockito.verify(telegramBot).sendMessage(update, "Введите тикер компании и количество лотов \nНапример : \nsber 40");

        assertEquals(UserState.STATE_POST_ORDER_RESPONSE, hashMap.get(123456L));
    }
}