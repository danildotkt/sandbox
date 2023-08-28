package io.sandbox.repository;

import io.sandbox.entity.TelegramUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(
        replace = AutoConfigureTestDatabase.Replace.NONE,
        connection = EmbeddedDatabaseConnection.H2
)
@EnableJpaRepositories
public class TelegramUserRepositoryTest {

    @Autowired
    private TelegramUserRepository telegramUserRepository;

    @Test
    void testFindById() {

        TelegramUser telegramUser = TelegramUser.builder()
                .chatId(124L)
                .sandboxToken("sandboxToken")
                .accountId("accountId")
                .build();

        telegramUserRepository.save(telegramUser);

        TelegramUser foundUser = telegramUserRepository.findById(telegramUser.getChatId()).orElse(null);

        assertNotNull(foundUser);
        assertEquals("accountId", foundUser.getAccountId());
    }
    @Test
    public void testExistById() {

        TelegramUser telegramUser = TelegramUser.builder()
                .chatId(124L)
                .sandboxToken("sandboxToken")
                .accountId("accountId")
                .build();

        telegramUserRepository.save(telegramUser);

        boolean exists = telegramUserRepository.existsById(telegramUser.getChatId());

        assertTrue(exists);
    }

    @Test
    public void testSave() {

        TelegramUser telegramUser = TelegramUser.builder()
                .chatId(124L)
                .sandboxToken("sandboxToken")
                .accountId("accountId")
                .build();

        TelegramUser savedUser = telegramUserRepository.save(telegramUser);

        assertEquals(telegramUser.getChatId(), savedUser.getChatId());
        assertEquals(telegramUser.getSandboxToken(), savedUser.getSandboxToken());
        assertEquals(telegramUser.getAccountId(), savedUser.getAccountId());
    }
}
