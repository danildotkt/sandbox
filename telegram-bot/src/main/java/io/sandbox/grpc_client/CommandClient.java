package io.sandbox.grpc_client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.sandbox.grpc.CommandServiceGrpc;
import io.sandbox.grpc.CommandServiceOuterClass;
import org.springframework.stereotype.Service;

@Service
public class CommandClient {

    private static final ManagedChannel channel = ManagedChannelBuilder
            .forAddress("localhost", 9093)
            .usePlaintext()
            .build();


    private static final CommandServiceGrpc.CommandServiceBlockingStub stub =  CommandServiceGrpc.newBlockingStub(channel);

    public static String createNewSandbox(String token){

        var request = CommandServiceOuterClass.CreateNewSandboxRequest
                .newBuilder()
                .setToken(token)
                .build();

        var response = stub.createNewSandbox(request);

        return response.getSandboxId();
    }

    public static double postOrderBuyMarket(long chatId, String quantity, String ticker){

        var request = CommandServiceOuterClass.PostOrderBuyMarketRequest
                .newBuilder()
                .setChatId(chatId)
                .setQuantity(quantity)
                .setTicker(ticker)
                .build();

        var response = stub.postOrderBuyMarket(request);

        return response.getExecutedOrderPrice();
    }

    public static String getInstrumentName(long chatId, String ticker){

        var request = CommandServiceOuterClass.GetInstrumentRequest
                .newBuilder()
                .setChatId(chatId)
                .setTicker(ticker)
                .build();

        var response = stub.getInstrument(request);

        return response.getShareName();
    }




}
