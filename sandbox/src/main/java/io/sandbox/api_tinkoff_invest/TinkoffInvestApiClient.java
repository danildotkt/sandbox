package io.sandbox.api_tinkoff_invest;

import io.sandbox.api_database.JpaServiceClient;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.*;

import java.util.List;

@Service
public class TinkoffInvestApiClient {
    
    private final JpaServiceClient jpaServiceClient;
    private final TinkoffInvestStub stub;

    public TinkoffInvestApiClient(TinkoffInvestStub stub, JpaServiceClient jpaServiceClient) {
        this.jpaServiceClient = jpaServiceClient;
        this.stub = stub;
    }

    public String createNewSandbox(String token) {

        var SandboxStub = stub.returnSandboxStub(token);

        OpenSandboxAccountRequest request = OpenSandboxAccountRequest.newBuilder().build();

        OpenSandboxAccountResponse response = SandboxStub.openSandboxAccount(request);

        GetAccountsRequest request2 = GetAccountsRequest.newBuilder().build();

        GetAccountsResponse response2 = SandboxStub.getSandboxAccounts(request2);

        List<Account> list = response2.getAccountsList();
        Account account = list.get(0);
        String accountId = account.getId();

        MoneyValue value = MoneyValue
                .newBuilder()
                .setCurrency("RUB")
                .setUnits(6_000_000)
                .build();

        SandboxPayInRequest request3 = SandboxPayInRequest
                .newBuilder()
                .setAccountId(accountId)
                .setAmount(value)
                .build();

        SandboxPayInResponse response3 = SandboxStub.sandboxPayIn(request3);

        return accountId;
    }

    public PostOrderResponse postOrderBuyMarket(long chatId, String ticker, String quantity){

        var sandboxToken = jpaServiceClient.getSandboxToken(chatId);
        var accountId = jpaServiceClient.getAccountId(chatId);

        var sandboxStub = stub.returnSandboxStub(sandboxToken);

        Share share = getInstrumentByTicker(chatId, ticker);
        String figi = share.getFigi();

        PostOrderRequest request4 = PostOrderRequest.newBuilder()
                .setAccountId(accountId)
                .setDirection(OrderDirection.ORDER_DIRECTION_BUY)
                .setOrderType(OrderType.ORDER_TYPE_MARKET)
                .setQuantity(Long.parseLong(quantity))
                .setInstrumentId(figi)
                .build();

        return sandboxStub.postSandboxOrder(request4);
    }

    public List<PortfolioPosition> sandboxPortfolio(long chatId){

        var sandboxToken = jpaServiceClient.getSandboxToken(chatId);
        var accountId = jpaServiceClient.getAccountId(chatId);

        var sandboxStub = stub.returnSandboxStub(sandboxToken);

        PortfolioRequest request = PortfolioRequest.newBuilder()
                .setAccountId(accountId)
                .build();

        PortfolioResponse response = sandboxStub.getSandboxPortfolio(request);

        return response.getPositionsList();
    }

    public List<Operation> sandboxOperations(long chatId){

        var sandboxToken = jpaServiceClient.getSandboxToken(chatId);
        var accountId = jpaServiceClient.getAccountId(chatId);
    
        var OperationStub = stub.returnOperationStub(sandboxToken);
    
        OperationsRequest request = OperationsRequest.newBuilder().setAccountId(accountId).build();
    
        OperationsResponse response = OperationStub.getOperations(request);
    
        var list = response.getOperationsList();
        return getLastTenOperations(list);
    }
    
    private List<Operation> getLastTenOperations(List<Operation> operationList){
        if(operationList.size() <= 10){
            return operationList;
        }
        return operationList.subList(operationList.size()-10 , operationList.size());
    }

    public Share getInstrumentByTicker(long chatId,String ticker){

        var sandboxToken = jpaServiceClient.getSandboxToken(chatId);

        var InstrumentStub = stub.returnInstrumentStub(sandboxToken);

        InstrumentRequest request3 = InstrumentRequest
                .newBuilder()
                .setIdType(InstrumentIdType.INSTRUMENT_ID_TYPE_TICKER)
                .setId(ticker.toUpperCase())
                .setClassCode("TQBR")
                .build();

        ShareResponse response3 = InstrumentStub.shareBy(request3);

        return response3.getInstrument();
    }

    public Instrument getInstrumentByFigi(long chatId, String figi) {

        var sandboxToken = jpaServiceClient.getSandboxToken(chatId);

        var InstrumentStub = stub.returnInstrumentStub(sandboxToken);

        InstrumentRequest request3 = InstrumentRequest
                .newBuilder()
                .setIdType(InstrumentIdType.INSTRUMENT_ID_TYPE_FIGI)
                .setId(figi)
                .build();

        InstrumentResponse response3 = InstrumentStub.getInstrumentBy(request3);
        return response3.getInstrument();
    }
}
