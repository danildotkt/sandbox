package io.sandbox.api_tinkoff_invest;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import ru.tinkoff.piapi.contract.v1.InstrumentsServiceGrpc;
import ru.tinkoff.piapi.contract.v1.OperationsServiceGrpc;
import ru.tinkoff.piapi.contract.v1.SandboxServiceGrpc;

public class TinkoffStub {

    private static final ManagedChannel channel = ManagedChannelBuilder
            .forAddress("sandbox-invest-public-api.tinkoff.ru", 443)
            .useTransportSecurity()
            .build();

    public static SandboxServiceGrpc.SandboxServiceBlockingStub returnSandboxStub(String token) {
        Metadata metadata = metadata(token);
        return SandboxServiceGrpc.newBlockingStub(channel)
                .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(metadata));
    }

    public static InstrumentsServiceGrpc.InstrumentsServiceBlockingStub returnInstrumentStub(String token) {
        Metadata metadata = metadata(token);
        return InstrumentsServiceGrpc.newBlockingStub(channel)
                .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(metadata));
    }

    public static OperationsServiceGrpc.OperationsServiceBlockingStub returnOperationStub(String token) {
        Metadata metadata = metadata(token);
        return OperationsServiceGrpc.newBlockingStub(channel)
                .withInterceptors(MetadataUtils.newAttachHeadersInterceptor(metadata));
    }

    private static Metadata metadata(String token) {
        Metadata metadata = new Metadata();
        metadata.put(Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER), "Bearer " + token);
        return metadata;
    }
}