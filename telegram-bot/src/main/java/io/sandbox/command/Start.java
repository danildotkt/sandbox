package io.sandbox.command;

import io.sandbox.model.User;
import io.sandbox.model.UserRepository;
import io.sandbox.service.TelegramBotService;
import io.sandbox.user_state.UserState;
import io.sandbox.utils.MessageUtils;
import io.sandbox.utils.TinkoffGrpcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.List;


@Component
public class Start implements Command{

    public void handleRequestState(HashMap<Long, UserState> hashMap, Update update, TelegramBotService telegramBotService){
        var chatId = update.getMessage().getChatId();
        String request = createNewSandboxRequest(update);
        var sendMessage = MessageUtils.sendMessage(update, request);
        if(request.equals("вы уже зарегестрированны в sandbox")){
            hashMap.remove(chatId);
            telegramBotService.sendMessage(sendMessage);
            return;
        }
        hashMap.remove(chatId);
        hashMap.put(chatId, UserState.STATE_START_RESPONSE);
        telegramBotService.sendMessage(sendMessage);
    }

    public  void handleResponseState(HashMap<Long, UserState> hashMap, Update update, TelegramBotService telegramBotService){
        var chatId = update.getMessage().getChatId();
        String response = createNewSandboxResponse(update);

        var sendMessage = MessageUtils.sendMessage(update, response);
        if(response.equals("Неверный формат токена")){
            telegramBotService.sendMessage(sendMessage);
            return;
        }
        hashMap.remove(chatId);
        hashMap.put(chatId, UserState.STATE_DEFAULT);
        telegramBotService.sendMessage(sendMessage);
    }

    private String createNewSandboxRequest(Update update){
        if(userRepository.findById(update.getMessage().getChatId()) .isPresent()){
            return "вы уже зарегестрированны в sandbox";
        }
        return "Добро пожаловать в sandbox " +"\n"+
                "введите свой токен от песочницы для того чтобы продолжить \n " +
                "https://www.tinkoff.ru/invest/settings/api/";
    }

}













