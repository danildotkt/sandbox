package io.sandbox.command;


//import io.sandbox.user_state.UserState;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.meta.api.objects.Update;
//
//import javax.sound.midi.Instrument;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class Operation {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public void responseStateHandler(HashMap<Long, UserState> hashMap, Update update, TelegramBotService telegramBotService){
//        hashMap.remove(update.getMessage().getChatId());
//        var responseList = response(update);
//        var sandboxToken = TinkoffGrpcUtils.getSandboxToken(update, userRepository);
//        for(ru.tinkoff.piapi.contract.v1.Operation list : responseList){
//
//            Instrument share = TinkoffGrpcUtils.getInstrument(sandboxToken, list.getFigi());
//            String info = share.getName()
//                    + "\nСтатус заявки : "
//                    + MessageUtils.operationStateParser(list.getState().toString())
//                    + "\nКоличество акций : "
//                    + list.getQuantity()
//                    + " шт"
//                    + "\nЦена за акцию : "
//                    + MessageUtils.round(Double.parseDouble(list.getPrice().getUnits() + "."+list.getPrice().getNano()))
//                    + " ₽"
//                    + "\nОбщая сумма : "
//                    + list.getPayment().getUnits()
//                    + " ₽";
//            telegramBotService.sendMessage(MessageUtils.sendMessage(update,info));
//        }
//    }
//
//    private List<ru.tinkoff.piapi.contract.v1.Operation> response(Update update){
//        Optional<User> userOptional = userRepository.findById(update.getMessage().getChatId());
//        User user = userOptional.get();
//        var sandboxToken = user.getSandboxToken();
//        var sandboxId = user.getSandboxId();
//
//        return sandboxOperations(sandboxToken, sandboxId);
//    }
//
//    private List<ru.tinkoff.piapi.contract.v1.Operation> sandboxOperations(String token, String accountId){
//
//        var OperationStub = TinkoffGrpcUtils.returnOperationStub(token);
//
//        OperationsRequest request = OperationsRequest.newBuilder().setAccountId(accountId).build();
//
//        OperationsResponse response = OperationStub.getOperations(request);
//
//        var list = response.getOperationsList();
//        return list.subList(list.size()-10 , list.size());
//    }
//
//}
