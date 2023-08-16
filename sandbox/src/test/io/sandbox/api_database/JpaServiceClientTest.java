package io.sandbox.api_database;

import io.grpc.Channel;
import io.sandbox.grpc.JpaServiceGrpc;
import io.sandbox.grpc.JpaServiceOuterClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.mockito.Mockito.mockStatic;

public class JpaServiceClientTest {

    @Test
    public void testIsExist() {
        long chatId = 123L;

        var request = JpaServiceOuterClass.ExistByIdRequest.newBuilder()
                .setChatId(chatId)
                .build();

        var response = JpaServiceOuterClass.ExistByIdResponse.newBuilder()
                .setExist(true)
                .build();

        JpaServiceGrpc.JpaServiceBlockingStub stub = Mockito.mock(JpaServiceGrpc.JpaServiceBlockingStub.class);
        Mockito.when(stub.existById(request)).thenReturn(response);

        try (MockedStatic<JpaServiceGrpc.JpaServiceBlockingStub> grpcMock = Mockito.mockStatic(JpaServiceGrpc.JpaServiceBlockingStub.class)) {
            grpcMock.when(() -> JpaServiceGrpc.newBlockingStub(Mockito.any(Channel.class))).thenReturn(stub);

            boolean result = JpaServiceClient.isExist(chatId);

            Assertions.assertTrue(result);
        }
    }
}