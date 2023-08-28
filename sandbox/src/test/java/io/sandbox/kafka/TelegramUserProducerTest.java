package io.sandbox.kafka;

import io.sandbox.entity.TelegramUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TelegramUserProducerTest {

    @Mock
    private KafkaTemplate<String, TelegramUser> kafkaTemplate;

    @Captor
    private ArgumentCaptor<Message<TelegramUser>> messageCaptor;

    @InjectMocks
    private TelegramUserProducer telegramUserProducer;

    @Test
    void testSendTelegramUser() {

        TelegramUser user = TelegramUser.builder()
                .chatId(123456789)
                .sandboxToken("sandboxToken")
                .accountId("accountId")
                .build();

        telegramUserProducer.sendTelegramUser(user);

        verify(kafkaTemplate).send(messageCaptor.capture());

        Message<TelegramUser> capturedMessage = messageCaptor.getValue();
        assertEquals("telegram_user_topic", capturedMessage.getHeaders().get(KafkaHeaders.TOPIC));
        assertEquals(user, capturedMessage.getPayload());
    }
}