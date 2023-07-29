package io.sandbox.service;



import io.grpc.stub.StreamObserver;
import io.sandbox.entity.TelegramUser;
import io.sandbox.grpc.TelegramUserGrpc;
import io.sandbox.grpc.TelegramUserServiceGrpc;
import io.sandbox.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TelegramUserServiceImpl extends TelegramUserServiceGrpc.TelegramUserServiceImplBase {

    private final UserRepository userRepository;

    public TelegramUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void getSandboxToken(TelegramUserGrpc.GetSandboxTokenRequest request,
                                StreamObserver<TelegramUserGrpc.GetSandboxTokenResponse> responseObserver) {

        Long chatId = request.getChatId();
        Optional<TelegramUser> user = userRepository.findById(chatId);

        TelegramUser telegramUser = user.get();

      TelegramUserGrpc.GetSandboxTokenResponse response = TelegramUserGrpc.GetSandboxTokenResponse.newBuilder()
                .setSandboxToken(telegramUser.getSandboxToken())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getSandboxId(TelegramUserGrpc.GetSandboxIdRequest request,
                                       StreamObserver<TelegramUserGrpc.GetSandboxIdResponse> responseObserver) {

        Long chatId = request.getChatId();
        Optional<TelegramUser> user = userRepository.findById(chatId);

        TelegramUser telegramUser = user.get();

        TelegramUserGrpc.GetSandboxIdResponse response = TelegramUserGrpc.GetSandboxIdResponse.newBuilder()
                .setSandboxId(telegramUser.getSandboxId())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
