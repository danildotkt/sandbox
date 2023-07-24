package io.sandbox.grpc_client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import org.springframework.stereotype.Component;
import ru.tinkoff.piapi.contract.v1.InstrumentsServiceGrpc;
import ru.tinkoff.piapi.contract.v1.OperationsServiceGrpc;
import ru.tinkoff.piapi.contract.v1.SandboxServiceGrpc;
import ru.tinkoff.piapi.contract.v1.UsersServiceGrpc;

@Component
public class TinkoffStub {

    private final ManagedChannel channel = ManagedChannelBuilder
            .forAddress("sandbox-invest-public-api.tinkoff.ru", 443)
            .useTransportSecurity()
            .build();

    private Metadata metadata(String token){

        Metadata metadata = new Metadata();
        metadata.put(Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER),
                "Bearer " + token);

        return metadata;

    }

    public SandboxServiceGrpc.SandboxServiceBlockingStub returnSandboxStub(String token){

        Metadata metadata = metadata(token);

        return SandboxServiceGrpc
                .newBlockingStub(channel)
                .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(metadata));
    }

    public InstrumentsServiceGrpc.InstrumentsServiceBlockingStub returnInstrumentStub(String token){

        Metadata metadata = metadata(token);

        return InstrumentsServiceGrpc
                .newBlockingStub(channel)
                .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(metadata));
    }

    public OperationsServiceGrpc.OperationsServiceBlockingStub returnOperationStub(String token){

        Metadata metadata = metadata(token);

        return OperationsServiceGrpc
                .newBlockingStub(channel)
                .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(metadata));
    }

    public UsersServiceGrpc.UsersServiceBlockingStub returnUserStub(String token){

        Metadata metadata = metadata(token);

        return UsersServiceGrpc
                .newBlockingStub(channel)
                .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(metadata));
    }
}
