package io.sandbox;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.sandbox.service.UserRepositoryService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class UserRepositoryApplication {
    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(UserRepositoryApplication.class, args);

        Server server = ServerBuilder.forPort(9091)
                .addService(new UserRepositoryService())
                .build();

        server.start();
        server.awaitTermination();

    }
}
