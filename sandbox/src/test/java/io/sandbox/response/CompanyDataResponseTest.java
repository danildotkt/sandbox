package io.sandbox.response;

import io.sandbox.api.tinkoff_invest.InvestApi;
import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.piapi.contract.v1.Share;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CompanyDataResponseTest {

    private InvestApi investApi;
    private TelegramBot telegramBot;
    private CompanyDataResponse companyDataResponse;
    private Update update;
    private Map<Long, UserState> hashMap;
    private Share share;
    private Message message;

    @BeforeEach
    public void setUp() {
        investApi = mock(InvestApi.class);
        share = mock(Share.class);
        telegramBot = mock(TelegramBot.class);
        companyDataResponse = new CompanyDataResponse(investApi);
        update = mock(Update.class);
        hashMap = new HashMap<>();
        message = mock(Message.class);
    }

    @Test
    public void TestSendResponse_WhenValidToken() {

        when(message.getText()).thenReturn("SBER");
        when(update.getMessage()).thenReturn(message);
        when(message.getChatId()).thenReturn(123L);
        when(investApi.getInstrumentByTicker(123L, "SBER")).thenReturn(share);

        companyDataResponse.sendResponse(update, hashMap, telegramBot);

        verify(telegramBot).sendMessage(update, "https://smart-lab.ru/q/SBER/f/y/");
    }

    @Test
    public void TestSendResponse_WhenInvalidToken() {

        when(update.getMessage()).thenReturn(message);
        when(message.getText()).thenReturn("invalidTicker");
        when(message.getChatId()).thenReturn(123L);

        companyDataResponse.sendResponse(update, hashMap, telegramBot);

        assertEquals(0, hashMap.size());
    }
}