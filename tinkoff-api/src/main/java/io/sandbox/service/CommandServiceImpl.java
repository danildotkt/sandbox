package io.sandbox.service;

import io.grpc.stub.StreamObserver;
import io.sandbox.grpc.CommandServiceGrpc;
import io.sandbox.grpc.CommandServiceOuterClass;
import io.sandbox.grpc_client.TinkoffInvestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandServiceImpl extends CommandServiceGrpc.CommandServiceImplBase {

    private TinkoffInvestService tinkoffInvestService;

    public CommandServiceImpl(TinkoffInvestService tinkoffInvestService) {
        this.tinkoffInvestService = tinkoffInvestService;
    }

    @Override
    public void createNewSandbox(CommandServiceOuterClass.CreateNewSandboxRequest request,
                                 StreamObserver<CommandServiceOuterClass.CreateNewSandboxResponse> responseObserver) {

        var token = request.getToken();
        var accountId = tinkoffInvestService.createNewSandbox(token);

        CommandServiceOuterClass.CreateNewSandboxResponse response = CommandServiceOuterClass
                .CreateNewSandboxResponse
                .newBuilder()
                .setSandboxId(accountId)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}





















