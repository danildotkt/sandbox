package io.sandbox.kafka;

import io.sandbox.entity.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class TelegramUserProducer{

    @Autowired
    private  KafkaTemplate<String, TelegramUser> kafkaTemplate;

    public void sendTelegramUser(TelegramUser user) {

        Message<TelegramUser> message = MessageBuilder
                .withPayload(user)
                .setHeader(KafkaHeaders.TOPIC, "telegram_user_topic")
                .build();

        kafkaTemplate.send(message);
    }
}
