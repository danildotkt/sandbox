package io.sandbox.response;

import io.sandbox.api.tinkoff_invest.InvestApi;
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

class BuyStockResponseTest {
    @Mock
    private InvestApi investApi;
    @Mock
    private TelegramBot telegramBot;
    @Mock
    private Update update;
    @Mock
    private Message message;

    private BuyStockResponse buyStockResponse;
    private Map<Long, UserState> hashMap;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        buyStockResponse = new BuyStockResponse(investApi);
        hashMap = new HashMap<>();
    }

    @Test
    void testSendResponse() {
        when(update.getMessage()).thenReturn(message);
        when(message.getChatId()).thenReturn(123L);
        buyStockResponse.sendResponse(update, hashMap, telegramBot);

        assertEquals(1, hashMap.size());
        assertEquals(UserState.STATE_BUY_STOCK_RESPONSE, hashMap.get(123L));
    }
}