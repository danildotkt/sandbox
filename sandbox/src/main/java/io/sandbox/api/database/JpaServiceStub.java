package io.sandbox.api.database;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.sandbox.grpc.JpaServiceGrpc;
import org.springframework.stereotype.Service;

@Service
public class JpaServiceStub {

    private final ManagedChannel channel = ManagedChannelBuilder
            .forAddress("localhost", 9091)
            .usePlaintext()
            .build();

    public JpaServiceGrpc.JpaServiceBlockingStub returnJpaServiceStub(){
        return JpaServiceGrpc.newBlockingStub(channel);
    }

}
