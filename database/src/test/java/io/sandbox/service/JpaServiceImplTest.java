package io.sandbox.service;

import io.grpc.stub.StreamObserver;
import io.sandbox.entity.TelegramUser;
import io.sandbox.grpc.JpaServiceOuterClass;
import io.sandbox.repository.TelegramUserRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.Optional;

import static org.mockito.Mockito.*;


class JpaServiceImplTest {

    @Mock
    private TelegramUserRepository telegramUserRepository;

    private JpaServiceImpl jpaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        jpaService = new JpaServiceImpl(telegramUserRepository);
    }

    @Test
    void testGetSandboxToken() {
        JpaServiceOuterClass.GetSandboxTokenRequest request = JpaServiceOuterClass.GetSandboxTokenRequest.newBuilder()
                .setChatId(123)
                .build();

        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setSandboxToken("sandboxToken");

        when(telegramUserRepository.findById(123L)).thenReturn(Optional.of(telegramUser));

        JpaServiceOuterClass.GetSandboxTokenResponse expectedResponse = JpaServiceOuterClass
                .GetSandboxTokenResponse
                .newBuilder()
                .setSandboxToken("sandboxToken")
                .build();

        StreamObserver<JpaServiceOuterClass.GetSandboxTokenResponse> responseObserver = mock(StreamObserver.class);
        jpaService.getSandboxToken(request, responseObserver);

        verify(responseObserver).onNext(expectedResponse);
        verify(responseObserver).onCompleted();
    }

    @Test
    void testGetAccountId() {
        JpaServiceOuterClass.GetAccountIdRequest request = JpaServiceOuterClass.GetAccountIdRequest.newBuilder()
                .setChatId(123)
                .build();

        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setAccountId("accountId");

        when(telegramUserRepository.findById(123L)).thenReturn(Optional.of(telegramUser));

        JpaServiceOuterClass.GetAccountIdResponse expectedResponse = JpaServiceOuterClass
                .GetAccountIdResponse
                .newBuilder()
                .setAccountId("accountId")
                .build();

        StreamObserver<JpaServiceOuterClass.GetAccountIdResponse> responseObserver = mock(StreamObserver.class);
        jpaService.getAccountId(request, responseObserver);

        verify(responseObserver).onNext(expectedResponse);
        verify(responseObserver).onCompleted();
    }

    @Test
    void testExistById() {
        JpaServiceOuterClass.ExistByIdRequest request = JpaServiceOuterClass.ExistByIdRequest.newBuilder()
                .setChatId(123)
                .build();

        when(telegramUserRepository.existsById(123L)).thenReturn(true);

        JpaServiceOuterClass.ExistByIdResponse expectedResponse = JpaServiceOuterClass
                .ExistByIdResponse
                .newBuilder()
                .setExist(true)
                .build();

        StreamObserver<JpaServiceOuterClass.ExistByIdResponse> responseObserver = mock(StreamObserver.class);
        jpaService.existById(request, responseObserver);

        verify(responseObserver).onNext(expectedResponse);
        verify(responseObserver).onCompleted();
    }
}