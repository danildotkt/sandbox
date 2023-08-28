package io.sandbox.api.tinkoff_invest;

import io.sandbox.api.database.JpaService;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.*;

import java.util.List;

@Service
public class TinkoffInvestApiClient implements InvestApi {
    
    private final JpaService jpaService;
    private final TinkoffInvestStub stub;

    public TinkoffInvestApiClient(TinkoffInvestStub stub, JpaService jpaService) {
        this.jpaService = jpaService;
        this.stub = stub;
    }


    public String createNewSandbox(String token) {
        var sandboxStub = stub.returnSandboxStub(token);
        String accountId = openSandboxAccount(sandboxStub);
        payInSandboxAccount(sandboxStub, accountId);
        return accountId;
    }

    private String openSandboxAccount(SandboxServiceGrpc.SandboxServiceBlockingStub stub) {
        OpenSandboxAccountRequest request = OpenSandboxAccountRequest.newBuilder().build();
        OpenSandboxAccountResponse response = stub.openSandboxAccount(request);
        return getSandboxAccountId(stub);
    }

    private String getSandboxAccountId(SandboxServiceGrpc.SandboxServiceBlockingStub stub) {
        GetAccountsRequest request = GetAccountsRequest.newBuilder().build();
        GetAccountsResponse response = stub.getSandboxAccounts(request);
        List<Account> list = response.getAccountsList();
        Account account = list.get(0);
        return account.getId();
    }

    private void payInSandboxAccount(SandboxServiceGrpc.SandboxServiceBlockingStub stub, String accountId) {
        MoneyValue value = MoneyValue.newBuilder().setCurrency("RUB").setUnits(6_000_000).build();
        SandboxPayInRequest request = SandboxPayInRequest.newBuilder().setAccountId(accountId).setAmount(value).build();
        SandboxPayInResponse response = stub.sandboxPayIn(request);
    }

    public PostOrderResponse postOrderBuyMarket(long chatId, String ticker, String quantity) {
        var sandboxToken = jpaService.getSandboxToken(chatId);
        var accountId = jpaService.getAccountId(chatId);
        var sandboxStub = stub.returnSandboxStub(sandboxToken);
        Share share = getInstrumentByTicker(chatId, ticker);
        String figi = share.getFigi();
        PostOrderRequest request = buildPostOrderRequest(accountId, quantity, figi);
        return sandboxStub.postSandboxOrder(request);
    }

    private PostOrderRequest buildPostOrderRequest(String accountId, String quantity, String figi) {
        return PostOrderRequest.newBuilder()
                .setAccountId(accountId)
                .setDirection(OrderDirection.ORDER_DIRECTION_BUY)
                .setOrderType(OrderType.ORDER_TYPE_MARKET)
                .setQuantity(Long.parseLong(quantity))
                .setInstrumentId(figi)
                .build();
    }

    public List<PortfolioPosition> sandboxPortfolio(long chatId) {
        var sandboxToken = jpaService.getSandboxToken(chatId);
        var accountId = jpaService.getAccountId(chatId);
        var sandboxStub = stub.returnSandboxStub(sandboxToken);
        PortfolioRequest request = buildPortfolioRequest(accountId);
        PortfolioResponse response = sandboxStub.getSandboxPortfolio(request);
        return response.getPositionsList();
    }

    private PortfolioRequest buildPortfolioRequest(String accountId) {
        return PortfolioRequest.newBuilder()
                .setAccountId(accountId)
                .build();
    }

    public List<Operation> sandboxOperations(long chatId) {
        var sandboxToken = jpaService.getSandboxToken(chatId);
        var accountId = jpaService.getAccountId(chatId);
        var operationStub = stub.returnOperationStub(sandboxToken);
        OperationsRequest request = buildOperationsRequest(accountId);
        OperationsResponse response = operationStub.getOperations(request);
        var operationList = response.getOperationsList();
        return getLastTenOperations(operationList);
    }

    private OperationsRequest buildOperationsRequest(String accountId) {
        return OperationsRequest.newBuilder()
                .setAccountId(accountId)
                .build();
    }

    private List<Operation> getLastTenOperations(List<Operation> operationList) {
        if (operationList.size() <= 10) {
            return operationList;
        }
        return operationList.subList(operationList.size() - 10, operationList.size());
    }

    public Share getInstrumentByTicker(long chatId, String ticker) {
        var sandboxToken = jpaService.getSandboxToken(chatId);
        var instrumentStub = stub.returnInstrumentStub(sandboxToken);
        InstrumentRequest request = buildInstrumentRequestByTicker(ticker);
        ShareResponse response = instrumentStub.shareBy(request);
        return response.getInstrument();
    }

    private InstrumentRequest buildInstrumentRequestByTicker(String ticker) {
        return InstrumentRequest.newBuilder()
                .setIdType(InstrumentIdType.INSTRUMENT_ID_TYPE_TICKER)
                .setId(ticker.toUpperCase())
                .setClassCode("TQBR")
                .build();
    }

    public Instrument getInstrumentByFigi(long chatId, String figi) {
        var sandboxToken = jpaService.getSandboxToken(chatId);
        var instrumentStub = stub.returnInstrumentStub(sandboxToken);
        InstrumentRequest request = buildInstrumentRequestByFigi(figi);
        InstrumentResponse response = instrumentStub.getInstrumentBy(request);
        return response.getInstrument();
    }

    private InstrumentRequest buildInstrumentRequestByFigi(String figi) {
        return InstrumentRequest.newBuilder()
                .setIdType(InstrumentIdType.INSTRUMENT_ID_TYPE_FIGI)
                .setId(figi)
                .build();
    }
}
