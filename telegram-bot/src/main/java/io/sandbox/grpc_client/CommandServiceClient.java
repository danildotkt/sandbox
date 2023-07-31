package io.sandbox.grpc_client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.sandbox.grpc.CommandServiceGrpc;
import io.sandbox.grpc.CommandServiceOuterClass;
import org.springframework.stereotype.Service;

@Service
public class CommandServiceClient {

    private final ManagedChannel channel = ManagedChannelBuilder
            .forAddress("localhost", 9090)

            .usePlaintext()
            .build();

    CommandServiceGrpc.CommandServiceBlockingStub stub =  CommandServiceGrpc.newBlockingStub(channel);


    public String createNewSandbox(String token){

        var request = CommandServiceOuterClass.CreateNewSandboxRequest
                .newBuilder()
                .setToken(token)
                .build();

        var response = stub.createNewSandbox(request);

        return response.getSandboxId();
    }


}
