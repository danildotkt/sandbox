package io.sandbox.grpc_client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.sandbox.grpc.TelegramUserServiceGrpc;
import org.springframework.stereotype.Service;

@Service
public class TelegramUserServiceStub {

    ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8081)
            .usePlaintext()
            .build();


    public TelegramUserServiceGrpc.TelegramUserServiceStub returnTGUserServiceStub(){
        return TelegramUserServiceGrpc.newStub(channel);
    }

}
