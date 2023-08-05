package io.sandbox.service;

import io.grpc.stub.StreamObserver;

import io.sandbox.grpc.CommandServiceGrpc;
import io.sandbox.grpc.CommandServiceOuterClass;
import io.sandbox.grpc_client.TinkoffInvestClient;
import io.sandbox.utils.MoneyValueParser;
import org.springframework.stereotype.Service;
import ru.tinkoff.piapi.contract.v1.MoneyValue;
import ru.tinkoff.piapi.contract.v1.QuotationOrBuilder;

@Service
public class CommandServiceImpl extends CommandServiceGrpc.CommandServiceImplBase {

    private final TinkoffInvestClient tinkoffInvestClient;

    public CommandServiceImpl(TinkoffInvestClient tinkoffInvestClient) {
        this.tinkoffInvestClient = tinkoffInvestClient;
    }

    @Override
    public void createNewSandbox(CommandServiceOuterClass.CreateNewSandboxRequest request,
                                 StreamObserver<CommandServiceOuterClass.CreateNewSandboxResponse> responseObserver) {

        var token = request.getToken();
        var accountId = tinkoffInvestClient.createNewSandbox(token);

        CommandServiceOuterClass.CreateNewSandboxResponse response = CommandServiceOuterClass
                .CreateNewSandboxResponse
                .newBuilder()
                .setSandboxId(accountId)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void postOrderBuyMarket(CommandServiceOuterClass.PostOrderBuyMarketRequest request,
                          StreamObserver<CommandServiceOuterClass.PostOrderBuyMarketResponse> responseObserver){

        var chatId = request.getChatId();
        var ticker = request.getTicker();
        var quantity = request.getQuantity();

        var orderInfo = tinkoffInvestClient.postOrderMarketBuy(chatId, quantity, ticker);

        var units = orderInfo.getExecutedOrderPrice().getUnits();
        var nanos = orderInfo.getExecutedOrderPrice().getNano();
        var executedOrderPrice = MoneyValueParser.parseUnitAndNanoToDouble(units, nanos);

        CommandServiceOuterClass.PostOrderBuyMarketResponse response = CommandServiceOuterClass
                .PostOrderBuyMarketResponse
                .newBuilder()
                .setExecutedOrderPrice(executedOrderPrice)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getInstrument(CommandServiceOuterClass.GetInstrumentRequest request,
                                StreamObserver<CommandServiceOuterClass.GetInstrumentResponse> responseObserver){

        var chatId = request.getChatId();
        var ticker = request.getTicker();

        var instrument = tinkoffInvestClient.getInstrument(chatId, ticker);
        var instrumentName = instrument.getName();

        CommandServiceOuterClass.GetInstrumentResponse response = CommandServiceOuterClass
                .GetInstrumentResponse
                .newBuilder()
                .setShareName(instrumentName)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}





















