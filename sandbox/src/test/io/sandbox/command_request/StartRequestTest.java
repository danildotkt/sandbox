package io.sandbox.command_request;

import io.sandbox.api_database.JpaServiceClientTest;
import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class StartRequestTest {

    @Mock
    private JpaServiceClientTest jpaServiceClient;

    @Mock
    private TelegramBot telegramBot;

    private StartRequest startRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        startRequest = new StartRequest(jpaServiceClient);
    }

    @Test
    void sendRequest_shouldSendMessage() {
        Update update = new Update(); // Создать объект Update для тестирования
        Map<Long, UserState> stateMap = new HashMap<>(); // Создать объект Map для тестирования

        // Мокировать вызов метода isExist в jpaServiceClient и вернуть значение true
        when(jpaServiceClient.isExist(anyLong())).thenReturn(true);

        // Вызвать метод sendRequest для тестирования
        startRequest.sendRequest(update, stateMap, telegramBot);

        // Проверить, что метод sendMessage был вызван у telegramBot
        verify(telegramBot, times(1)).sendMessage(eq(update), anyString());
    }

    // Добавьте другие тесты по мере необходимости
}