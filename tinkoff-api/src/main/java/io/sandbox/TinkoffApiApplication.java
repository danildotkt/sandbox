package io.sandbox;

import io.grpc.ServerBuilder;
import io.sandbox.grpc_client.TelegramUserRepositoryClient;
import io.sandbox.grpc_client.TinkoffInvestClient;
import io.sandbox.grpc_client.TinkoffStub;
import io.sandbox.service.CommandServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class TinkoffApiApplication {
    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(TinkoffApiApplication.class, args);

        var server = ServerBuilder.forPort(9093)
                .addService(new CommandServiceImpl(new TinkoffInvestClient(new TinkoffStub(), new TelegramUserRepositoryClient())))
                .build();

        server.start();
        server.awaitTermination();
    }
}
