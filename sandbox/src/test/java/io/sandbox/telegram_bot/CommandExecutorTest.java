package io.sandbox.telegram_bot;

import io.sandbox.api.database.JpaService;
import io.sandbox.api.tinkoff_invest.InvestApi;
import io.sandbox.factory.CommandFactory;
import io.sandbox.kafka.TelegramUserProducer;
import io.sandbox.request.CommandRequest;
import io.sandbox.response.CommandResponse;
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
import static org.mockito.Mockito.*;

public class CommandExecutorTest {

    private CommandExecutor commandExecutor;

    @Mock
    private CommandFactory commandFactory;

    @Mock
    private TelegramBot telegramBot;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        commandExecutor = new CommandExecutor(commandFactory);
    }

    @Test
    void testExecuteCommand_requestState() {
        long chatId = 12345L;
        UserState userState = UserState.STATE_START_REQUEST;
        Map<Long, UserState> stateMap = new HashMap<>();
        stateMap.put(chatId, userState);

        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.getMessage()).thenReturn(message);
        when(message.getChatId()).thenReturn(chatId);

        CommandRequest request = mock(CommandRequest.class);
        when(commandFactory.createRequest(userState)).thenReturn(request);

        commandExecutor.executeCommand(stateMap, update, telegramBot);

        verify(request).sendRequest(update, stateMap, telegramBot);
    }

    @Test
    void testExecuteCommand_responseState() {
        long chatId = 12345L;
        UserState userState = UserState.STATE_PORTFOLIO_RESPONSE;
        Map<Long, UserState> stateMap = new HashMap<>();
        stateMap.put(chatId, userState);

        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.getMessage()).thenReturn(message);
        when(message.getChatId()).thenReturn(chatId);

        CommandResponse response = mock(CommandResponse.class);
        when(commandFactory.createResponse(userState)).thenReturn(response);

        commandExecutor.executeCommand(stateMap, update, telegramBot);

        verify(response).sendResponse(update, stateMap, telegramBot);
    }
}