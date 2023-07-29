package io.sandbox.service;

import io.grpc.stub.StreamObserver;
import io.sandbox.entity.TelegramUser;
import io.sandbox.grpc.TelegramUserProto;
import io.sandbox.repository.UserRepository;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl extends io.sandbox.grpc.UserServiceGrpc.UserServiceImplBase {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void getSandboxToken(TelegramUserProto.GetSandboxTokenRequest request,
                                       StreamObserver<TelegramUserProto.GetSandboxTokenResponse> responseObserver) {

        Long chatId = request.getChatId();
        Optional<TelegramUser> user = userRepository.findById(chatId);

        TelegramUser telegramUser = user.get();

        TelegramUserProto.GetSandboxTokenResponse response = TelegramUserProto.GetSandboxTokenResponse.newBuilder()
                .setSandboxToken(telegramUser.getSandboxToken())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getSandboxId(TelegramUserProto.GetSandboxIdRequest request,
                                       StreamObserver<TelegramUserProto.GetSandboxIdResponse> responseObserver) {

        Long chatId = request.getChatId();
        Optional<TelegramUser> user = userRepository.findById(chatId);

        TelegramUser telegramUser = user.get();

        TelegramUserProto.GetSandboxIdResponse response = TelegramUserProto.GetSandboxIdResponse.newBuilder()
                .setSandboxId(telegramUser.getSandboxId())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
