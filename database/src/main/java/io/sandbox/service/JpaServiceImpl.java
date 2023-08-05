package io.sandbox.service;


import io.grpc.stub.StreamObserver;
import io.sandbox.entity.TelegramUser;
import io.sandbox.grpc.JpaServiceGrpc;
import io.sandbox.grpc.JpaServiceOuterClass;
import io.sandbox.repository.TelegramUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaServiceImpl extends JpaServiceGrpc.JpaServiceImplBase {

    private final TelegramUserRepository telegramUserRepository;

    public JpaServiceImpl(TelegramUserRepository telegramUserRepository) {
        this.telegramUserRepository = telegramUserRepository;
    }

    @Override
    public void getSandboxToken(JpaServiceOuterClass.GetSandboxTokenRequest request,
                                StreamObserver<JpaServiceOuterClass.GetSandboxTokenResponse> responseObserver) {

        Long chatId = request.getChatId();
        Optional<TelegramUser> user = telegramUserRepository.findById(chatId);

        TelegramUser telegramUser = user.get();

        JpaServiceOuterClass.GetSandboxTokenResponse response = JpaServiceOuterClass
                .GetSandboxTokenResponse
                .newBuilder()
                .setSandboxToken(telegramUser.getSandboxToken())
                .build();


        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAccountId(JpaServiceOuterClass.GetAccountIdRequest request,
                                StreamObserver<JpaServiceOuterClass.GetAccountIdResponse> responseObserver) {

        Long chatId = request.getChatId();
        Optional<TelegramUser> user = telegramUserRepository.findById(chatId);

        TelegramUser telegramUser = user.get();

        JpaServiceOuterClass.GetAccountIdResponse response = JpaServiceOuterClass
                .GetAccountIdResponse
                .newBuilder()
                .setAccountId(telegramUser.getAccountId())
                .build();


        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void existById(JpaServiceOuterClass.ExistByIdRequest request,
                             StreamObserver<JpaServiceOuterClass.ExistByIdResponse> responseObserver) {

        Long chatId = request.getChatId();

        boolean isTelegramUserExist = telegramUserRepository.existsById(chatId);

        JpaServiceOuterClass.ExistByIdResponse response = JpaServiceOuterClass
                .ExistByIdResponse
                .newBuilder()
                .setExist(isTelegramUserExist)
                .build();


        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
