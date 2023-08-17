package io.sandbox.command_response;

import io.sandbox.api_tinkoff_invest.TinkoffInvestApiClient;
import io.sandbox.command_request.PostOrderRequest;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CompanyDataResponseTest {

    @Mock
    private TinkoffInvestApiClient tinkoffInvestApiClient;

    @Mock
    private TelegramBot telegramBot;

    private CompanyDataResponse companyDataResponse;
    private Map<Long, UserState> hashMap;
    private Update update;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        companyDataResponse = new CompanyDataResponse(tinkoffInvestApiClient);
        hashMap = new HashMap<>();
        update = new Update();
        update.getMessage().setText("ticker");
        update.getMessage().setChatId(123L);
    }

    @Test
    public void testSwitchStateResponse_removesFromHashMapWhenResponseIsUrl() {
        // Arrange
        hashMap.put(123L, new UserState());

        // Act
        companyDataResponse.sendResponse(update, hashMap);

        // Assert
        assertEquals(0, hashMap.size());
    }

    // Add more tests for different scenarios if needed
}