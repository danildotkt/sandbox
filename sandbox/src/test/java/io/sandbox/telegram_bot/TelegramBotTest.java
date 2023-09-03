package io.sandbox.telegram_bot;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static org.mockito.Mockito.*;

class TelegramBotTest {

    @Mock
    private Update update;

    @Mock
    private TelegramUpdateHandler telegramUpdateHandler;

    @InjectMocks
    private TelegramBot telegramBot;


    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testOnUpdateReceived() {
        Update update = new Update();
        Message message = new Message();
        message.setText("Hello");
        update.setMessage(message);

        telegramBot.onUpdateReceived(update);

        verify(telegramUpdateHandler).updateHandle(update);
    }

    @Test
    void sendMessage() throws TelegramApiException {
        String text = "Hello, world!";
        TelegramBot telegramBot = mock(TelegramBot.class);
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.getMessage()).thenReturn(message);
        when(message.getChatId()).thenReturn(123L);

        SendMessage expectedMessage = new SendMessage();
        expectedMessage.setChatId(123L);
        expectedMessage.setText(text);

        telegramBot.execute(expectedMessage);

        verify(telegramBot, times(1)).execute(expectedMessage);
    }
}
