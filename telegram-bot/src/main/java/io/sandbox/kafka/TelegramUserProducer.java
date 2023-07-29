package io.sandbox.kafka;


import io.sandbox.dto.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TelegramUserProducer{

    private final KafkaTemplate<String, TelegramUser> kafkaTemplate;

    @Autowired
    public TelegramUserProducer(KafkaTemplate<String, TelegramUser> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendTelegramUser(String topic, TelegramUser user) {
        kafkaTemplate.send(topic, user);
    }

}
