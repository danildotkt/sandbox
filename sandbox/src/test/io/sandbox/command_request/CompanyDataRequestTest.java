package io.sandbox.command_request;

import io.sandbox.command_response.StartResponse;
import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CompanyDataRequestTest {

    @Test
    public void testSendRequest() {
        // Создаем mock-объекты для зависимостей
        Update update = Mockito.mock(Update.class);
        Message message = Mockito.mock(Message.class);
        Map<Long, UserState> hashMap = new HashMap<>();
        TelegramBot telegramBot = Mockito.mock(TelegramBot.class);

        Mockito.when(update.getMessage()).thenReturn(message);
        Mockito.when(message.getChatId()).thenReturn(123456L);

        // Создаем экземпляр класса CompanyDataRequest
        CompanyDataRequest request = new CompanyDataRequest();

        // Вызываем метод sendRequest
        request.sendRequest(update, hashMap, telegramBot);

        // Проверяем, что методы были вызваны с правильными аргументами
        Mockito.verify(telegramBot).sendMessage(Mockito.eq(update), Mockito.anyString());
        Mockito.verify(update.getMessage()).getChatId();
        Mockito.verify(hashMap).remove(Mockito.anyLong());
        Mockito.verify(hashMap).put(Mockito.anyLong(), Mockito.eq(UserState.STATE_COMPANY_DATA_RESPONSE));
    }
}