package io.sandbox.api_database;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.sandbox.grpc.JpaServiceGrpc;
import io.sandbox.grpc.JpaServiceOuterClass;
import org.springframework.stereotype.Service;

@Service
public class JpaServiceClient {

    private static final ManagedChannel channel = ManagedChannelBuilder
            .forAddress("localhost", 9091)
            .usePlaintext()
            .build();

    private static final JpaServiceGrpc.JpaServiceBlockingStub stub =
            JpaServiceGrpc.newBlockingStub(channel);


    public static boolean isExist(long chatId) {

        var request = JpaServiceOuterClass.ExistByIdRequest
                .newBuilder()
                .setChatId(chatId)
                .build();

        return stub.existById(request).getExist();

    }

    public static String getSandboxToken(long chatId) {

        var request = JpaServiceOuterClass.GetSandboxTokenRequest
                        .newBuilder()
                        .setChatId(chatId)
                        .build();

        JpaServiceOuterClass.GetSandboxTokenResponse response = stub.getSandboxToken(request);

        return response.getSandboxToken();
    }

    public static String getAccountId(long chatId) {
        var request = JpaServiceOuterClass.GetAccountIdRequest
                .newBuilder()
                .setChatId(chatId)
                .build();

        JpaServiceOuterClass.GetAccountIdResponse response = stub.getAccountId(request);

        return response.getAccountId();
    }

}