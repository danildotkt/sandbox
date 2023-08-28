package io.sandbox.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TelegramUserTest {

    TelegramUser telegramUser;

    @BeforeEach
    void setup(){
        telegramUser = new TelegramUser(124L, "sandboxToken", "accountId");
    }


    @Test
    void testGetChatId() {
        assertEquals(124L, telegramUser.getChatId());
    }

    @Test
    void testGetSandboxToken() {
        assertEquals("sandboxToken", telegramUser.getSandboxToken());
    }

    @Test
    void testGetAccountId() {
        assertEquals("accountId", telegramUser.getAccountId());
    }

    @Test
    void testSetChatId() {
        telegramUser.setChatId(125L);
        assertEquals(125L, telegramUser.getChatId());
    }

    @Test
    void testSetSandboxToken() {
        telegramUser.setSandboxToken("Token");
        assertEquals("Token", telegramUser.getSandboxToken());
    }

    @Test
    void testSetAccountId() {
        telegramUser.setAccountId("ID");
        assertEquals("ID", telegramUser.getAccountId());
    }

    @Test
    void testBuilder() {
        TelegramUser telegramUser = TelegramUser.builder()
                .chatId(124L)
                .sandboxToken("sandboxToken")
                .accountId("accountId")
                .build();

        assertEquals(124L, telegramUser.getChatId());
        assertEquals("sandboxToken", telegramUser.getSandboxToken());
        assertEquals("accountId", telegramUser.getAccountId());
    }
}