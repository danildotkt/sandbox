package io.sandbox.api.database;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.sandbox.grpc.JpaServiceGrpc;
import io.sandbox.grpc.JpaServiceOuterClass;
import org.springframework.stereotype.Service;

@Service
public class JpaServiceClient implements JpaService{

    private final JpaServiceStub jpaServiceStub;

    public JpaServiceClient(JpaServiceStub jpaServiceStub) {
        this.jpaServiceStub = jpaServiceStub;
    }

    public boolean isExist(long chatId) {
        var stub = jpaServiceStub.returnJpaServiceStub();

        var request = JpaServiceOuterClass.ExistByIdRequest
                .newBuilder()
                .setChatId(chatId)
                .build();

        return stub.existById(request).getExist();
    }

    public String getSandboxToken(long chatId) {
        var stub = jpaServiceStub.returnJpaServiceStub();

        var request = JpaServiceOuterClass.GetSandboxTokenRequest
                        .newBuilder()
                        .setChatId(chatId)
                        .build();

        JpaServiceOuterClass.GetSandboxTokenResponse response = stub.getSandboxToken(request);

        return response.getSandboxToken();
    }

    public String getAccountId(long chatId) {
        var stub = jpaServiceStub.returnJpaServiceStub();

        var request = JpaServiceOuterClass.GetAccountIdRequest
                .newBuilder()
                .setChatId(chatId)
                .build();

        JpaServiceOuterClass.GetAccountIdResponse response = stub.getAccountId(request);

        return response.getAccountId();
    }
}