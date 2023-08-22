package io.sandbox.api_tinkoff_invest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.tinkoff.piapi.contract.v1.InstrumentsServiceGrpc;
import ru.tinkoff.piapi.contract.v1.OperationsServiceGrpc;
import ru.tinkoff.piapi.contract.v1.SandboxServiceGrpc;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TinkoffInvestStubTest {

    private TinkoffInvestStub tinkoffInvestStub;

    @BeforeEach
    public void setUp() {
        tinkoffInvestStub = new TinkoffInvestStub();
    }

    @Test
    public void testReturnSandboxStub() {
        String token = "your_token";

        SandboxServiceGrpc.SandboxServiceBlockingStub stub = tinkoffInvestStub.returnSandboxStub(token);

        assertNotNull(stub);
    }

    @Test
    public void testReturnInstrumentStub() {
        String token = "your_token";

        InstrumentsServiceGrpc.InstrumentsServiceBlockingStub stub = tinkoffInvestStub.returnInstrumentStub(token);

        assertNotNull(stub);
    }

    @Test
    public void testReturnOperationStub() {
        String token = "your_token";

        OperationsServiceGrpc.OperationsServiceBlockingStub stub = tinkoffInvestStub.returnOperationStub(token);

        assertNotNull(stub);
    }
}