package io.sandbox.command_response;

import io.sandbox.api_tinkoff_invest.InvestApi;
import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class OperationsResponseTest {

    @Mock
    private InvestApi investApi;
    @Mock
    private TelegramBot telegramBot;
    @Mock
    private Update update;
    @Mock
    private Message message;

    private OperationsResponse operationsResponse;
    private Map<Long, UserState> hashMap;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        operationsResponse = new OperationsResponse(investApi);
        hashMap = new HashMap<>();
    }

    @Test
    void testSendResponse_When() {
        // Arrange
        when(update.getMessage()).thenReturn(message);
        when(message.getChatId()).thenReturn(123L);

        // Act
        operationsResponse.sendResponse(update, hashMap, telegramBot);

        // Assert
        assertEquals(0, hashMap.size());
    }
}