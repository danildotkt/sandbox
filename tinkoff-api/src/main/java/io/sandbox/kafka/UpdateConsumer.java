package io.sandbox.kafka;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UpdateConsumer {

    @KafkaListener(topics = "update-topic",
                   groupId = "updateGroup")
    public void consume(Update update){
        System.out.println(update);
    }
}
