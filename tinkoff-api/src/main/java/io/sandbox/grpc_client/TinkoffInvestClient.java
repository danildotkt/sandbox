package io.sandbox.grpc_client;

import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.*;

import java.util.List;

@Service
public class TinkoffInvestClient {

    private final TinkoffStub tinkoffStub;
    private final TelegramUserRepositoryClient telegramUserRepositoryClient;


    public TinkoffInvestClient(TinkoffStub tinkoffStub, TelegramUserRepositoryClient telegramUserRepositoryClient) {
        this.tinkoffStub = tinkoffStub;
        this.telegramUserRepositoryClient = telegramUserRepositoryClient;
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

    public PostOrderResponse postOrderMarketBuy(long chatId, String ticker, String quantity){

        var sandboxToken = getSandboxToken(chatId);
        var accountId = getAccountId(chatId);

        var sandboxStub = tinkoffStub.returnSandboxStub(sandboxToken);
        var instrumentStub = tinkoffStub.returnInstrumentStub(sandboxToken);

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

//    public PostOrderResponse posOrderMarketSell(long chatId, String ticker, String quantity){
//
//        var sandboxToken = getSandboxToken(chatId);
//        var accountId = getAccountId(chatId);
//
//        var SandboxStub = tinkoffStub.returnSandboxStub(sandboxToken);
//        var InstrumentStub = tinkoffStub.returnInstrumentStub(sandboxToken);
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
//                .setDirection(OrderDirection.ORDER_DIRECTION_SELL)
//                .setOrderType(OrderType.ORDER_TYPE_MARKET)
//                .setQuantity(Long.parseLong(quantity))
//                .setInstrumentId(figi)
//                .build();
//
//        return SandboxStub.postSandboxOrder(request4);
//    }

//    private List<PortfolioPosition> sandboxPortfolio(long chatId){
//
//        var sandboxToken = getSandboxToken(chatId);
//        var accountId = getAccountId(chatId);
//
//        var sandboxStub = tinkoffStub.returnSandboxStub(sandboxToken);
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
//    private List<ru.tinkoff.piapi.contract.v1.Operation> sandboxOperations(long chatId){
//
//        var sandboxToken = getSandboxToken(chatId);
//        var accountId = getAccountId(chatId);
//
//        var OperationStub = tinkoffStub.returnOperationStub(sandboxToken);
//
//        OperationsRequest request = OperationsRequest.newBuilder().setAccountId(accountId).build();
//
//        OperationsResponse response = OperationStub.getOperations(request);
//
//        var list = response.getOperationsList();
//        return list.subList(list.size()-10 , list.size());
//    }
//
    public Share getInstrument(long chatId,String ticker){

        var sandboxToken = getSandboxToken(chatId);

        var InstrumentStub = tinkoffStub.returnInstrumentStub(sandboxToken);

        InstrumentRequest request3 = InstrumentRequest
                .newBuilder()
                .setIdType(InstrumentIdType.INSTRUMENT_ID_TYPE_TICKER)
                .setId(ticker.toUpperCase())
                .setClassCode("TQBR")
                .build();

        ShareResponse response3 = InstrumentStub.shareBy(request3);

        return response3.getInstrument();
    }

    private String getSandboxToken(long chatId){
        return telegramUserRepositoryClient.getSandboxToken(chatId);
    }
    private String getAccountId(long chatId){
        return telegramUserRepositoryClient.getAccountId(chatId);
    }
}
