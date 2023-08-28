package io.sandbox.api.database;

import io.sandbox.grpc.JpaServiceGrpc;
import io.sandbox.grpc.JpaServiceOuterClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JpaServiceClientTest {

    private JpaServiceClient jpaServiceClient;
    private JpaServiceStub jpaServiceStub;

    @BeforeEach
    void setUp() {
        jpaServiceStub = Mockito.mock(JpaServiceStub.class);
        jpaServiceClient = new JpaServiceClient(jpaServiceStub);
    }

    @Test
    void testIsExist() {
        long chatId = 123;
        boolean expectedExist = true;

        JpaServiceOuterClass.ExistByIdRequest expectedRequest = JpaServiceOuterClass.ExistByIdRequest.newBuilder()
                .setChatId(chatId)
                .build();

        JpaServiceOuterClass.ExistByIdResponse response = JpaServiceOuterClass.ExistByIdResponse.newBuilder()
                .setExist(expectedExist)
                .build();

        Mockito.when(jpaServiceStub.returnJpaServiceStub()).thenReturn(Mockito.mock(JpaServiceGrpc.JpaServiceBlockingStub.class));
        Mockito.when(jpaServiceStub.returnJpaServiceStub().existById(expectedRequest)).thenReturn(response);

        boolean actualExist = jpaServiceClient.isExist(chatId);

        assertEquals(expectedExist, actualExist);
    }

    @Test
    void testGetSandboxToken() {
        long chatId = 123;
        String expectedToken = "sandbox-token";

        JpaServiceOuterClass.GetSandboxTokenRequest expectedRequest = JpaServiceOuterClass.GetSandboxTokenRequest.newBuilder()
                .setChatId(chatId)
                .build();

        JpaServiceOuterClass.GetSandboxTokenResponse response = JpaServiceOuterClass.GetSandboxTokenResponse.newBuilder()
                .setSandboxToken(expectedToken)
                .build();

        Mockito.when(jpaServiceStub.returnJpaServiceStub()).thenReturn(Mockito.mock(JpaServiceGrpc.JpaServiceBlockingStub.class));
        Mockito.when(jpaServiceStub.returnJpaServiceStub().getSandboxToken(expectedRequest)).thenReturn(response);

        String actualToken = jpaServiceClient.getSandboxToken(chatId);

        assertEquals(expectedToken, actualToken);
    }

    @Test
    void testGetAccountId() {

        long chatId = 123;
        String expectedAccountId = "account-id";

        JpaServiceOuterClass.GetAccountIdRequest expectedRequest = JpaServiceOuterClass.GetAccountIdRequest.newBuilder()
                .setChatId(chatId)
                .build();

        JpaServiceOuterClass.GetAccountIdResponse response = JpaServiceOuterClass.GetAccountIdResponse.newBuilder()
                .setAccountId(expectedAccountId)
                .build();

        Mockito.when(jpaServiceStub.returnJpaServiceStub()).thenReturn(Mockito.mock(JpaServiceGrpc.JpaServiceBlockingStub.class));
        Mockito.when(jpaServiceStub.returnJpaServiceStub().getAccountId(expectedRequest)).thenReturn(response);

        String actualAccountId = jpaServiceClient.getAccountId(chatId);

        assertEquals(expectedAccountId, actualAccountId);
    }
}