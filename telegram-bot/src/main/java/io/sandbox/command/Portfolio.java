package io.sandbox.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;


//@Component
//public class Portfolio {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public void responseStateHandler(HashMap<Long, UserState> hashMap, Update update, TelegramBotService telegramBotService){
//        var message = update.getMessage();
//        hashMap.remove(message.getChatId());
//        var token = TinkoffGrpcUtils.getSandboxToken(update, userRepository);
//        var accountId = TinkoffGrpcUtils.getAccountId(update, userRepository);
//        var positionsList = sandboxPortfolio(token, accountId);
////        var balance = MessageUtils.roundForDoubleValue(Double.parseDouble(positionsList.get(0).getQuantity().getUnits()+"."
////                +positionsList.get(0).getQuantity().getUnits()));
//
//        if(message.getChat().getLastName() != null) {
//            telegramBotService.sendMessage(new SendMessage(update.getMessage().getChatId().toString(),
//                    message.getChat().getFirstName() + " "
//                            + message.getChat().getLastName() + "  \uD83E\uDD13"));
//        }
//        else{
//            telegramBotService.sendMessage(new SendMessage(update.getMessage().getChatId().toString(),
//                    message.getChat().getFirstName() + "  \uD83E\uDD13"));
//        }
//
//        for(var position : positionsList){
//            var figi = position.getFigi();
//
//            var instrument = TinkoffGrpcUtils.getInstrument(token, figi);
//            var avgPosition = MessageUtils.round(Double.parseDouble(position.getAveragePositionPrice().getUnits()+"."+position.getAveragePositionPrice().getNano()));
//            var curPosition = MessageUtils.round(Double.parseDouble(position.getCurrentPrice().getUnits()+"."+position.getCurrentPrice().getNano()));
//
//            if(instrument.getName().equals("Российский рубль")){
//                continue;
//            }
//
//            String info = instrument.getName()
//                    + "\n"
//                    + position.getQuantity().getUnits()
//                    + " акций "
//                    + "\n"
//                    + avgPosition
//                    + "  ->  "
//                    + curPosition + " ₽"
//                    ;
//            telegramBotService.sendMessage(new SendMessage(update.getMessage().getChatId().toString(), info));
//        }
//
//    }
//    private List<PortfolioPosition> sandboxPortfolio(String token,String accountId){
//        var sandboxStub = TinkoffGrpcUtils.returnSandboxStub(token);
//
//        PortfolioRequest request = PortfolioRequest.newBuilder()
//                .setAccountId(accountId)
//                .build();
//
//        PortfolioResponse response = sandboxStub.getSandboxPortfolio(request);
//
//        return response.getPositionsList();
//    }
//}
