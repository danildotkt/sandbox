package io.sandbox.kafka;

import io.sandbox.entity.TelegramUser;
import io.sandbox.repository.TelegramUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TelegramUserConsumerTest {

    @Mock
    private TelegramUserRepository telegramUserRepository;

    @InjectMocks
    private TelegramUserConsumer telegramUserConsumer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSetToDatabase() {
        TelegramUser telegramUser = TelegramUser.builder().sandboxToken("token").chatId(124L).accountId("id").build();
        this.telegramUserConsumer.saveToDatabase(telegramUser);
        Mockito.verify(this.telegramUserRepository, Mockito.times(1)).save(telegramUser);
    }
}