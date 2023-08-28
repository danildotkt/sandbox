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

    @Mock
    private TelegramUserProducer telegramUserProducer;
    @Mock
    private InvestApi investApi;
    @Mock
    private JpaService jpaService;
    @Mock
    private Update update;
    @Mock
    private Message message;
    @Mock
    private TelegramBot telegramBot;
    @Mock
    CommandResponse commandResponse;
    @Mock
    CommandRequest commandRequest;
    @Mock
    CommandFactory commandFactory;

    private CommandExecutor commandExecutor;
    private Map<Long, UserState> stateMap;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        commandExecutor = new CommandExecutor(commandFactory);
        stateMap = new HashMap<>();
    }

    @Test
    public void testExecuteCommand_WhenStateIsRequest() {
        // Arrange
        stateMap.put(123L, UserState.STATE_START_REQUEST);
        when(update.getMessage()).thenReturn(message);
        when(message.getChatId()).thenReturn(123L);
        when(UserState.STATE_START_REQUEST.isRequest()).thenReturn(true);

        // Act
        commandExecutor.executeCommand(stateMap, update, telegramBot);

        // Assert
        // Verify that the corresponding CommandRequest is created and executed
        verify(commandRequest).sendRequest(update, stateMap, telegramBot);
    }

    @Test
    public void testExecuteCommand_WhenStateIsResponse() {
        // Arrange
        stateMap.put(123L, UserState.STATE_START_RESPONSE);
        when(update.getMessage()).thenReturn(message);
        when(message.getChatId()).thenReturn(123L);
        when(UserState.STATE_START_RESPONSE.isResponse()).thenReturn(true);

        // Act
        commandExecutor.executeCommand(stateMap, update, telegramBot);

        // Assert
        // Verify that the corresponding CommandResponse is created and executed
        verify(commandResponse).sendResponse(update, stateMap, telegramBot);
    }

    @Test
    public void testExecuteCommand_WhenStateIsDefault() {
        // Arrange
        stateMap.put(123L, UserState.STATE_DEFAULT);
        when(update.getMessage()).thenReturn(message);
        when(message.getText()).thenReturn("start");
        when(message.getChatId()).thenReturn(123L);

        // Act
        commandExecutor.executeCommand(stateMap, update, telegramBot);

        // Assert
        // Verify that the state is updated and executeCommand is called recursively
        assertEquals(UserState.STATE_START_REQUEST, stateMap.get(123L));
        verify(commandExecutor, times(2)).executeCommand(stateMap, update, telegramBot);
    }
}
