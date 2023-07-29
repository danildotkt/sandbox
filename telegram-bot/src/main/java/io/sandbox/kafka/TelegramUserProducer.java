package io.sandbox.kafka;


import io.sandbox.dto.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class TelegramUserProducer{

    private final KafkaTemplate<String, TelegramUser> kafkaTemplate;

    public TelegramUserProducer(KafkaTemplate<String, TelegramUser> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTelegramUser(TelegramUser user) {

        Message<TelegramUser> message = MessageBuilder
                .withPayload(user)
                .setHeader(KafkaHeaders.TOPIC, "new_user_topic")
                .build();
        
        kafkaTemplate.send(message);
    }

}
