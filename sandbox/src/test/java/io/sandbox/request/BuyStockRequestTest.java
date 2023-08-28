package io.sandbox.request;

import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import io.sandbox.utils.message.PostOrderMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

class BuyStockRequestTest {

    @Mock
    private Update update;

    @Mock
    private TelegramBot telegramBot;

    private BuyStockRequest buyStockRequest;
    private Map<Long, UserState> hashMap;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        buyStockRequest = new BuyStockRequest();
        hashMap = new HashMap<>();

        Message message = mock(Message.class);
        when(message.getChatId()).thenReturn(123L);
        when(update.getMessage()).thenReturn(message);
    }

    @Test
    void testSendRequest() {
        when(update.getMessage().getChatId()).thenReturn(123L);
        buyStockRequest.sendRequest(update, hashMap, telegramBot);
        verify(telegramBot, times(1)).sendMessage(update, PostOrderMessage.tickerPromptMessage());
    }
}