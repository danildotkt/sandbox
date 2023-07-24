package io.sandbox.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UpdateProducer {

    @Autowired
    private KafkaTemplate<String, Update> kafkaTemplate;

    public void sendUpdate(Update update){
        var topic = "update-topic";
        kafkaTemplate.send(topic, update);

    }
}
