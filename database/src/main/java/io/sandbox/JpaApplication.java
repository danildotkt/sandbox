package io.sandbox;



import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.sandbox.repository.TelegramUserRepository;
import io.sandbox.service.JpaServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class JpaApplication {

    private static TelegramUserRepository telegramUserRepository;

    public JpaApplication(TelegramUserRepository telegramUserRepository) {
        JpaApplication.telegramUserRepository = telegramUserRepository;
    }

    public static void main(String[] args) throws IOException, InterruptedException {

        SpringApplication.run(JpaApplication.class, args);

        Server server = ServerBuilder
                .forPort(9091)
                .addService(new JpaServiceImpl(telegramUserRepository))
                .build();

        server.start();
        server.awaitTermination();

    }
}
