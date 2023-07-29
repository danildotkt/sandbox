package io.sandbox.kafka;

import io.sandbox.entity.TelegramUser;
import io.sandbox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TelegramUserConsumer {

    private final UserRepository userRepository;

    @Autowired
    public TelegramUserConsumer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @KafkaListener(topics = "new_user_topic",
                    groupId = "new_user_group")
    public void saveToDataBase(TelegramUser telegramUser){
        userRepository.save(telegramUser);
    }

}
