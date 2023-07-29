package io.sandbox.grpc_client;

import io.grpc.stub.StreamObserver;
import io.sandbox.grpc.TelegramUserGrpc;
import io.sandbox.grpc.TelegramUserServiceGrpc;
import org.springframework.stereotype.Service;

@Service
public class TelegramUserServiceClient {

    private final TelegramUserServiceStub telegramStub;

    public TelegramUserServiceClient(TelegramUserServiceStub telegramStub) {
        this.telegramStub = telegramStub;
    }


    public String getSandboxToken(long chatId){

        var stub = telegramStub.returnTGUserServiceStub();

        var request = TelegramUserGrpc.GetSandboxTokenRequest.newBuilder()
                .setChatId(chatId)
                .build();

        var responseObserver = new StreamObserver<TelegramUserGrpc.GetSandboxTokenResponse>() {

            private String sandboxToken;

            @Override
            public void onNext(TelegramUserGrpc.GetSandboxTokenResponse response) {
                this.sandboxToken = response.getSandboxToken();
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
            }

            public String getSandboxToken() {
                return sandboxToken;
            }
        };


        stub.getSandboxToken(request, responseObserver);

        return responseObserver.getSandboxToken();
    }

}
