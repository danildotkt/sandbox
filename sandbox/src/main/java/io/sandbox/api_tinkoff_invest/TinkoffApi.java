package io.sandbox.api_tinkoff_invest;

import io.sandbox.api_database.JpaServiceClient;
import ru.tinkoff.piapi.contract.v1.*;

import java.util.List;

public class TinkoffApi {

    public static String createNewSandbox(String token) {

        var SandboxStub = TinkoffStub.returnSandboxStub(token);

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

    public static PostOrderResponse postOrderBuyMarket(long chatId, String ticker, String quantity){

        var sandboxToken = JpaServiceClient.getSandboxToken(chatId);
        var accountId = JpaServiceClient.getAccountId(chatId);

        var sandboxStub = TinkoffStub.returnSandboxStub(sandboxToken);
        var instrumentStub = TinkoffStub.returnInstrumentStub(sandboxToken);

        InstrumentRequest request3 = InstrumentRequest
                .newBuilder()
                .setIdType(InstrumentIdType.INSTRUMENT_ID_TYPE_TICKER)
                .setId(ticker)
                .setClassCode("TQBR")
                .build();

        ShareResponse response3 = instrumentStub.shareBy(request3);

        Share share = response3.getInstrument();
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

    public static List<PortfolioPosition> sandboxPortfolio(long chatId){

        var sandboxToken = JpaServiceClient.getSandboxToken(chatId);
        var accountId = JpaServiceClient.getAccountId(chatId);

        var sandboxStub = TinkoffStub.returnSandboxStub(sandboxToken);

        PortfolioRequest request = PortfolioRequest.newBuilder()
                .setAccountId(accountId)
                .build();

        PortfolioResponse response = sandboxStub.getSandboxPortfolio(request);

        return response.getPositionsList();
    }

    public static List<Operation> sandboxOperations(long chatId){

    var sandboxToken = JpaServiceClient.getSandboxToken(chatId);
    var accountId = JpaServiceClient.getAccountId(chatId);

    var OperationStub = TinkoffStub.returnOperationStub(sandboxToken);

    OperationsRequest request = OperationsRequest.newBuilder().setAccountId(accountId).build();

    OperationsResponse response = OperationStub.getOperations(request);

    var list = response.getOperationsList();
    return list.subList(list.size()-10 , list.size());
}

    public static Share getInstrumentByTicker(long chatId,String ticker){

        var sandboxToken = JpaServiceClient.getSandboxToken(chatId);

        var InstrumentStub = TinkoffStub.returnInstrumentStub(sandboxToken);

        InstrumentRequest request3 = InstrumentRequest
                .newBuilder()
                .setIdType(InstrumentIdType.INSTRUMENT_ID_TYPE_TICKER)
                .setId(ticker.toUpperCase())
                .setClassCode("TQBR")
                .build();

        ShareResponse response3 = InstrumentStub.shareBy(request3);

        return response3.getInstrument();
    }

    public static Instrument getInstrumentByFigi(long chatId, String figi) {

        var sandboxToken = JpaServiceClient.getSandboxToken(chatId);

        var InstrumentStub = TinkoffStub.returnInstrumentStub(sandboxToken);

        InstrumentRequest request3 = InstrumentRequest
                .newBuilder()
                .setIdType(InstrumentIdType.INSTRUMENT_ID_TYPE_FIGI)
                .setId(figi)
                .build();

        InstrumentResponse response3 = InstrumentStub.getInstrumentBy(request3);
        return response3.getInstrument();
    }
}
