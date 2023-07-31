package io.sandbox.service;


import io.grpc.stub.StreamObserver;
import io.sandbox.entity.TelegramUser;
import io.sandbox.grpc.UserRepositoryServiceGrpc;
import io.sandbox.grpc.UserRepositoryServiceOuterClass;
import io.sandbox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

//import io.grpc.stub.StreamObserver;
//import io.sandbox.entity.TelegramUser;
//import io.sandbox.grpc.TelegramUserGrpc;
//import io.sandbox.grpc.TelegramUserServiceGrpc;
//import io.sandbox.repository.UserRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
@Service
public class UserRepositoryService extends UserRepositoryServiceGrpc.UserRepositoryServiceImplBase {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void getSandboxToken(UserRepositoryServiceOuterClass.GetSandboxTokenRequest request,
                                StreamObserver<UserRepositoryServiceOuterClass.GetSandboxTokenResponse> responseObserver) {

        Long chatId = request.getChatId();
        Optional<TelegramUser> user = userRepository.findById(chatId);

        TelegramUser telegramUser = user.get();

        UserRepositoryServiceOuterClass.GetSandboxTokenResponse response = UserRepositoryServiceOuterClass
                .GetSandboxTokenResponse
                .newBuilder()
                .setSandboxToken(telegramUser.getSandboxToken())
                .build();


        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getSandboxId(UserRepositoryServiceOuterClass.GetSandboxIdRequest request,
                                StreamObserver<UserRepositoryServiceOuterClass.GetSandboxIdResponse> responseObserver) {

        Long chatId = request.getChatId();
        Optional<TelegramUser> user = userRepository.findById(chatId);

        TelegramUser telegramUser = user.get();

        UserRepositoryServiceOuterClass.GetSandboxIdResponse response = UserRepositoryServiceOuterClass
                .GetSandboxIdResponse
                .newBuilder()
                .setSandboxId(telegramUser.getSandboxId())
                .build();


        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
