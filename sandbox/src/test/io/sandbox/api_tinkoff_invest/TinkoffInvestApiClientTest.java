package io.sandbox.api_tinkoff_invest;

import io.sandbox.api_database.JpaServiceClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.tinkoff.piapi.contract.v1.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TinkoffInvestApiClientTest {

    @Mock
    private TinkoffInvestStub stub;
    @InjectMocks
    private TinkoffInvestApiClient apiClient;
    @Mock
    private JpaServiceClient jpaServiceClient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateNewSandbox() {
        String token = "your_token";
        String accountId = "your_account_id";

        SandboxServiceGrpc.SandboxServiceBlockingStub sandboxStub = mock(SandboxServiceGrpc.SandboxServiceBlockingStub.class);
        OpenSandboxAccountResponse openResponse = OpenSandboxAccountResponse.newBuilder().build();
        GetAccountsResponse getAccountsResponse = GetAccountsResponse.newBuilder()
                .addAccounts(Account.newBuilder().setId(accountId).build())
                .build();
        SandboxPayInResponse payInResponse = SandboxPayInResponse.newBuilder().build();

        when(stub.returnSandboxStub(token)).thenReturn(sandboxStub);
        when(sandboxStub.openSandboxAccount(any(OpenSandboxAccountRequest.class))).thenReturn(openResponse);
        when(sandboxStub.getSandboxAccounts(any(GetAccountsRequest.class))).thenReturn(getAccountsResponse);
        when(sandboxStub.sandboxPayIn(any(SandboxPayInRequest.class))).thenReturn(payInResponse);

        String createdAccountId = apiClient.createNewSandbox(token);

        assertEquals(accountId, createdAccountId);
    }
}