package io.sandbox.api_database;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.sandbox.grpc.JpaServiceGrpc;
import io.sandbox.grpc.JpaServiceOuterClass;
import org.springframework.stereotype.Service;

@Service
public class JpaServiceClient implements JpaService{

    private final ManagedChannel channel = ManagedChannelBuilder
            .forAddress("localhost", 9091)
            .usePlaintext()
            .build();

    private final JpaServiceGrpc.JpaServiceBlockingStub stub =
            JpaServiceGrpc.newBlockingStub(channel);


    public boolean isExist(long chatId) {

        var request = JpaServiceOuterClass.ExistByIdRequest
                .newBuilder()
                .setChatId(chatId)
                .build();

        return stub.existById(request).getExist();

    }

    public String getSandboxToken(long chatId) {

        var request = JpaServiceOuterClass.GetSandboxTokenRequest
                        .newBuilder()
                        .setChatId(chatId)
                        .build();

        JpaServiceOuterClass.GetSandboxTokenResponse response = stub.getSandboxToken(request);

        return response.getSandboxToken();
    }

    public String getAccountId(long chatId) {
        var request = JpaServiceOuterClass.GetAccountIdRequest
                .newBuilder()
                .setChatId(chatId)
                .build();

        JpaServiceOuterClass.GetAccountIdResponse response = stub.getAccountId(request);

        return response.getAccountId();
    }
}