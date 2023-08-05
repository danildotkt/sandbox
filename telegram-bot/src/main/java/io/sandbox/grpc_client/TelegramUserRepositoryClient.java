package io.sandbox.grpc_client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.sandbox.grpc.UserRepositoryServiceGrpc;
import io.sandbox.grpc.UserRepositoryServiceOuterClass;
import org.springframework.stereotype.Service;

@Service
public class TelegramUserRepositoryClient {

    private static final ManagedChannel channel = ManagedChannelBuilder
            .forAddress("localhost", 9094)
            .usePlaintext()
            .build();

    private static final UserRepositoryServiceGrpc.UserRepositoryServiceBlockingStub stub =
            UserRepositoryServiceGrpc.newBlockingStub(channel);

    public static boolean isExistUserInDatabase(long chatId){

        var request = UserRepositoryServiceOuterClass.CheckTelegramUserInDatabaseRequest
                .newBuilder()
                .setChatId(chatId)
                .build();

        return stub.existByIdTelegramUser(request).getExist();
    }



}
