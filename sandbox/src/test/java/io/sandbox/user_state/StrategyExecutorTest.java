package io.sandbox.user_state;

import io.sandbox.api.database.JpaServiceClient;
import io.sandbox.api.tinkoff_invest.TinkoffInvestApiClient;
import io.sandbox.request.StartRequest;
import io.sandbox.telegram_bot.CommandExecutor;
import io.sandbox.telegram_bot.TelegramBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class StrategyExecutorTest {

    @Mock
    TinkoffInvestApiClient investApi;

    @Mock
    JpaServiceClient jpaServiceClient;

    @Mock
    TelegramBot telegramBot;

    @InjectMocks
    CommandExecutor commandExecutor;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUserStateSwitch() {

        Update update = mock(Update.class);
        when(update.getMessage()).thenReturn(mock(Message.class));
        when(update.getMessage().getChatId()).thenReturn(123L);

        Map<Long, UserState> userMap = new HashMap<>();
        userMap.put(123L, UserState.STATE_START_REQUEST);

        StartRequest requestStrategy = spy(new StartRequest(jpaServiceClient));

        commandExecutor.executeCommand(userMap, update, telegramBot);

        verifyNoInteractions(investApi);
    }
}