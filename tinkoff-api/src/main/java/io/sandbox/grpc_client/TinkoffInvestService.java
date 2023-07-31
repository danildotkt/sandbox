package io.sandbox.grpc_client;

import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.*;

import java.util.List;

@Service
public class TinkoffInvestService {

    private final TinkoffStub tinkoffStub;

    public TinkoffInvestService(TinkoffStub tinkoffStub) {
        this.tinkoffStub = tinkoffStub;
    }

    public String createNewSandbox(String token) {

        var SandboxStub = tinkoffStub.returnSandboxStub(token);

        OpenSandboxAccountRequest request1 = OpenSandboxAccountRequest.newBuilder().build();

        OpenSandboxAccountResponse response1 = SandboxStub.openSandboxAccount(request1);

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

//    private MoneyValue postSandboxOrderMarket(String ticker, String quantity){
//
//        var SandboxStub = tinkoffStub.returnSandboxStub(token);
//        var InstrumentStub = tinkoffStub.returnInstrumentStub(token);
//
//
//        InstrumentRequest request3 = InstrumentRequest
//                .newBuilder()
//                .setIdType(InstrumentIdType.INSTRUMENT_ID_TYPE_TICKER)
//                .setId(ticker)
//                .setClassCode("TQBR")
//                .build();
//
//        ShareResponse response3 = InstrumentStub.shareBy(request3);
//
//        Share share = response3.getInstrument();
//        String figi = share.getFigi();
//
//        PostOrderRequest request4 = PostOrderRequest.newBuilder()
//                .setAccountId(accountId)
//                .setDirection(OrderDirection.ORDER_DIRECTION_BUY)
//                .setOrderType(OrderType.ORDER_TYPE_MARKET)
//                .setQuantity(Long.parseLong(quantity))
//                .setInstrumentId(figi)
//                .build();
//
//        PostOrderResponse response4 = SandboxStub.postSandboxOrder(request4);
//
//        return response4.getExecutedOrderPrice();
//    }
//
//    private List<PortfolioPosition> sandboxPortfolio(){
//
//        var sandboxStub = tinkoffStub.returnSandboxStub(token);
//
//        PortfolioRequest request = PortfolioRequest.newBuilder()
//                .setAccountId(accountId)
//                .build();
//
//        PortfolioResponse response = sandboxStub.getSandboxPortfolio(request);
//
//        return response.getPositionsList();
//    }
//
//    private List<ru.tinkoff.piapi.contract.v1.Operation> sandboxOperations(){
//
//        var OperationStub = tinkoffStub.returnOperationStub(token);
//
//        OperationsRequest request = OperationsRequest.newBuilder().setAccountId(accountId).build();
//
//        OperationsResponse response = OperationStub.getOperations(request);
//
//        var list = response.getOperationsList();
//        return list.subList(list.size()-10 , list.size());
//    }
//
//    public Instrument getInstrument(String figi){
//
//        var InstrumentStub = tinkoffStub.returnInstrumentStub(token);
//
//        InstrumentRequest request3 = InstrumentRequest
//                .newBuilder()
//                .setIdType(InstrumentIdType.INSTRUMENT_ID_TYPE_FIGI)
//                .setId(figi)
//                .build();
//
//        InstrumentResponse response3 = InstrumentStub.getInstrumentBy(request3);
//
//        return response3.getInstrument();
//    }
}
