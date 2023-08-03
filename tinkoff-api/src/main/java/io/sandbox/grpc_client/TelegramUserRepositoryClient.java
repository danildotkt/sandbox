package io.sandbox.grpc_client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.sandbox.grpc.UserRepositoryServiceGrpc;
import io.sandbox.grpc.UserRepositoryServiceOuterClass;
import org.springframework.stereotype.Service;

@Service
public class TelegramUserRepositoryClient {

    private final ManagedChannel channel = ManagedChannelBuilder
            .forAddress("localhost", 9094)
            .usePlaintext()
            .build();

    UserRepositoryServiceGrpc.UserRepositoryServiceBlockingStub stub = UserRepositoryServiceGrpc.newBlockingStub(channel);

    public String getSandboxToken(long chatId){
        var request = UserRepositoryServiceOuterClass.GetSandboxTokenRequest
                .newBuilder()
                .setChatId(chatId)
                .build();

        var response = stub.getSandboxToken(request);
        return response.getSandboxToken();
    }

    public String getAccountId(long chatId){
        var request = UserRepositoryServiceOuterClass.GetSandboxIdRequest
                .newBuilder()
                .setChatId(chatId)
                .build();

        var response = stub.getSandboxId(request);
        return response.getSandboxId();
    }
}
