package io.sandbox.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TelegramUserTest {

    @Test
    void testGetChatId() {
        TelegramUser telegramUser = new TelegramUser(124L, "sandboxToken", "accountId");
        Assertions.assertEquals(124L, telegramUser.getChatId());
    }

    @Test
    void testGetSandboxToken() {
        TelegramUser telegramUser = new TelegramUser(124L, "sandboxToken", "accountId");
        Assertions.assertEquals("sandboxToken", telegramUser.getSandboxToken());
    }

    @Test
    void testGetAccountId() {
        TelegramUser telegramUser = new TelegramUser(124L, "sandboxToken", "accountId");
        Assertions.assertEquals("accountId", telegramUser.getAccountId());
    }

    @Test
    void testSetChatId() {
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId(125L);
        Assertions.assertEquals(125L, telegramUser.getChatId());
    }

    @Test
    void testSetSandboxToken() {
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setSandboxToken("sandboxToken");
        Assertions.assertEquals("sandboxToken", telegramUser.getSandboxToken());
    }

    @Test
    void testSetAccountId() {
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setAccountId("accountId");
        Assertions.assertEquals("accountId", telegramUser.getAccountId());
    }

    @Test
    void testBuilder() {
        TelegramUser telegramUser = TelegramUser.builder()
                .chatId(124L)
                .sandboxToken("sandboxToken")
                .accountId("accountId")
                .build();

        Assertions.assertEquals(124L, telegramUser.getChatId());
        Assertions.assertEquals("sandboxToken", telegramUser.getSandboxToken());
        Assertions.assertEquals("accountId", telegramUser.getAccountId());
    }
}