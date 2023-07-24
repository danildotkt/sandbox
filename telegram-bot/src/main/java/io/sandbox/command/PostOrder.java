package io.sandbox.command;

//import io.grpc.Status;
//import io.grpc.StatusRuntimeException;
//import io.sandbox.model.UserRepository;
//import io.sandbox.service.TelegramBotService;
//import io.sandbox.user_state.UserState;
//import io.sandbox.utils.MessageUtils;
//import io.sandbox.utils.TinkoffGrpcUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.objects.Update;
//
//import java.util.HashMap;
//
//
//@Component
//public class PostOrder {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public void requestStateHandler(HashMap<Long, UserState> hashMap, Update update, TelegramBotService telegramBotService){
//        var chatId = update.getMessage().getChatId();
//        hashMap.remove(chatId);
//        String a = request();
//        hashMap.put(chatId, UserState.STATE_POST_ORDER_RESPONSE);
//        telegramBotService.sendMessage(MessageUtils.sendMessage(update, a));
//    }
//
//    public  void responseStateHandler(HashMap<Long, UserState> hashMap, Update update, TelegramBotService telegramBotService){
//        var chatId = update.getMessage().getChatId();
//        try {
//            var a =  response(update);
//            hashMap.remove(chatId);
//            telegramBotService.sendMessage(MessageUtils.sendMessage(update, a));
//        } catch (StatusRuntimeException | ArrayIndexOutOfBoundsException | NumberFormatException ex){
//            hashMap.put(chatId, UserState.STATE_POST_ORDER_RESPONSE);
//            var a =  "попробуйте снова";
//            telegramBotService.sendMessage(MessageUtils.sendMessage(update, a));
//        }
//
//    }
//
//    private String request(){
//        return "Введите тикер компании и количество лотов \nНапример : \nsber 40";
//    }
//
//    private String response(Update update){
//
//
//        String token = TinkoffGrpcUtils.getSandboxToken(update, userRepository);
//        String inputMessage = update.getMessage().getText();
//
//        String[] inputMessageArr = inputMessage.split(" ", 2);
//        String ticker = inputMessageArr[0];
//
//
//        int quantity = Integer.parseInt(inputMessageArr[1]);
//
//        if(quantity <= 0){
//            throw new StatusRuntimeException(Status.INVALID_ARGUMENT);
//        }
//
//        return postSandboxOrderMarket(token, ticker.toUpperCase(), String.valueOf(quantity), update);
//    }
//
//    private String postSandboxOrderMarket(String token, String ticker, String quantity, Update update){
//
//        var SandboxStub = TinkoffGrpcUtils.returnSandboxStub(token);
//        var InstrumentStub = TinkoffGrpcUtils.returnInstrumentStub(token);
//
//        String accountId = TinkoffGrpcUtils.getAccountId(update, userRepository);
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
//        MoneyValue price = response4.getExecutedOrderPrice();
//
//        double stockPrice = Double.parseDouble(price.getUnits() +"."+price.getNano());
//        String roundedStockPrice = MessageUtils.round(stockPrice);
//
//        return ("Заявка исполнена, было куплено " + quantity + " акций компании \n" + share.getName() + " \nпо цене "
//                + roundedStockPrice + " ₽");
//    }
//}
