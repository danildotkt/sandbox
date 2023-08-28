package io.sandbox.response;

import io.sandbox.api.tinkoff_invest.InvestApi;
import io.sandbox.kafka.TelegramUserProducer;
import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import io.sandbox.utils.message.StartMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class StartResponseTest {

    @Mock
    private TelegramUserProducer telegramUserProducer;

    @Mock
    private InvestApi investApi;

    @Mock
    private TelegramBot telegramBot;

    private StartResponse startResponse;
    private Map<Long, UserState> stateMap;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        startResponse = new StartResponse(telegramUserProducer, investApi);
        stateMap = new HashMap<>();
    }

    @Test
    void testSendResponse() {
        Update update = mock(Update.class);
        Message message = mock(Message.class);

        when(update.getMessage()).thenReturn(message);
        when(message.getChatId()).thenReturn(123L);

        startResponse.sendResponse(update, stateMap, telegramBot);

        verify(telegramBot, times(1)).sendMessage(update, StartMessage.successCreateSandboxMessage());
        assertEquals(UserState.STATE_DEFAULT, stateMap.get(123L));
    }
}