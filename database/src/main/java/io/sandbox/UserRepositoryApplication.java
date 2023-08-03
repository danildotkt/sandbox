package io.sandbox;



import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.sandbox.repository.UserRepository;
import io.sandbox.service.UserRepositoryServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class UserRepositoryApplication {

    private static UserRepository userRepository;

    public UserRepositoryApplication(UserRepository userRepository) {
        UserRepositoryApplication.userRepository = userRepository;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        SpringApplication.run(UserRepositoryApplication.class, args);



        Server server = ServerBuilder.forPort(9094)
                .addService(new UserRepositoryServiceImpl(userRepository))
                .build();

        server.start();
        server.awaitTermination();

    }
}
