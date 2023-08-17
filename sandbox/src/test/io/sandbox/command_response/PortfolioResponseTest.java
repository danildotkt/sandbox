package io.sandbox.command_response;

import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.ConcurrentHashMap;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class PortfolioResponseTest {

    @Mock
    Update update;

    @Mock
    ConcurrentHashMap<Long, UserState> stateMap;

    @Mock
    TelegramBot telegramBot;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testSendMessage() {
        Message message = mock(Message.class);
        when(update.getMessage()).thenReturn(message);
        when(message.getChatId()).thenReturn(123456789L);

        StartResponse.sendResponse(update, stateMap, telegramBot);

        verify(telegramBot, times(1)).sendMessage(eq(update), anyString());
    }
}