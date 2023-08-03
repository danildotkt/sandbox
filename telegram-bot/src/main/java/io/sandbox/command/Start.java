package io.sandbox.command;
import io.sandbox.grpc_client.CommandClient;
import io.sandbox.grpc_client.TelegramUserRepositoryClient;
import io.sandbox.kafka.TelegramUserProducer;
import io.sandbox.entity.TelegramUser;
import io.sandbox.telegrambot.TelegramBotService;
import io.sandbox.user_state.UserState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

@Component
public class Start implements Command{
    private final TelegramUserProducer telegramUserProducer;

    public Start(TelegramUserProducer telegramUserProducer) {
        this.telegramUserProducer = telegramUserProducer;
    }

    public void sendTelegramMessageRequest(Update update, HashMap<Long, UserState> stateMap, TelegramBotService telegramBotService){
        telegramBotService.sendMessage(update , switchStateRequest(update, stateMap));
    }

    private String switchStateRequest(Update update, HashMap<Long, UserState> stateMap){
        long chatId = update.getMessage().getChatId();
        stateMap.remove(chatId);
        if(checkUserExistInDatabase(chatId)){
            stateMap.put(chatId, UserState.STATE_DEFAULT);
            return "Вы уже зарегестрированны в sandbox";
        }
        stateMap.put(chatId, UserState.STATE_START_RESPONSE);
        return "Введите токен sandbox ";

    }

    private boolean checkUserExistInDatabase(long chatId){
        return TelegramUserRepositoryClient.isExistUserInDatabase(chatId);
    }

    public void sendTelegramMessageResponse(Update update, HashMap<Long, UserState> stateMap, TelegramBotService telegramBotService){
        telegramBotService.sendMessage(update, switchStateResponse(update, stateMap));
    }

    private String switchStateResponse(Update update, HashMap<Long, UserState> stateMap){
        long chatId = update.getMessage().getChatId();
        var response = createNewSandbox(update);
        stateMap.remove(chatId);
        if(response.equals("Попробуйте ввести токен снова")){
            stateMap.put(chatId, UserState.STATE_START_RESPONSE);
            return response;
        }
        stateMap.put(chatId, UserState.STATE_DEFAULT);
        return response;

    }

    private String createNewSandbox(Update update){

        var chatId = update.getMessage().getChatId();
        var inputMessage = update.getMessage().getText();

        try {
            var accountId = CommandClient.createNewSandbox(inputMessage);

            TelegramUser newTelegramUser = TelegramUser
                    .builder()
                    .chatId(chatId)
                    .sandboxToken(inputMessage)
                    .sandboxId(accountId)
                    .build();

            saveTelegramUser(newTelegramUser);

        }catch (Exception e ){
            System.out.println(e.getMessage());
            return "Попробуйте ввести токен снова";
        }
        return "Ваш sandbox счет успешно создан и на него зачисленно 6 миллионов рублей";
    }

    private void saveTelegramUser(TelegramUser user){
        telegramUserProducer.sendTelegramUser(user);
    }
}













