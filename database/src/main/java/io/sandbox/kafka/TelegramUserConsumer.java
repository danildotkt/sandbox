package io.sandbox.kafka;

import io.sandbox.entity.TelegramUser;
import io.sandbox.repository.TelegramUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TelegramUserConsumer {

    private final TelegramUserRepository telegramUserRepository;

    @Autowired
    public TelegramUserConsumer(TelegramUserRepository telegramUserRepository) {
        this.telegramUserRepository = telegramUserRepository;
    }

    @KafkaListener(topics = "telegram_user_topic",
                    groupId = "new_user_group")
    public void saveToDatabase(TelegramUser telegramUser){
        telegramUserRepository.save(telegramUser);
    }
}



















