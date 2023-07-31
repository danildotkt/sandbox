package io.sandbox.command;

import io.sandbox.entity.TelegramUser;
import io.sandbox.enums.TokenStatus;

import io.sandbox.grpc_client.CommandServiceClient;
import io.sandbox.kafka.TelegramUserProducer;
import io.sandbox.telegrambot.TelegramBotService;
import io.sandbox.user_state.UserState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

@Component
public class Start {

    private final CommandServiceClient commandServiceClient;
    private final TelegramUserProducer telegramUserProducer;

    public Start(CommandServiceClient commandServiceClient, TelegramUserProducer telegramUserProducer) {
        this.commandServiceClient = commandServiceClient;
        this.telegramUserProducer = telegramUserProducer;
    }

    public void sendTelegramMessageRequest(Update update, HashMap<Long, UserState> stateMap, TelegramBotService telegramBotService){
        telegramBotService.sendMessage(update , switchStateRequest(update, stateMap));
    }

    public void sendTelegramMessageResponse(Update update, HashMap<Long, UserState> stateMap, TelegramBotService telegramBotService){
        telegramBotService.sendMessage(update, switchStateResponse(update, stateMap));
    }

    private String switchStateRequest(Update update, HashMap<Long, UserState> stateMap){
        long chatId = update.getMessage().getChatId();
        stateMap.remove(chatId);
        stateMap.put(chatId, UserState.STATE_START_RESPONSE);
        return commandRequest();

    }
    private String switchStateResponse(Update update, HashMap<Long, UserState> stateMap){
        long chatId = update.getMessage().getChatId();
        var response = commandResponse(update);
        stateMap.remove(chatId);
        if(response.equals("Попробуйте ввести токен снова")){
            stateMap.put(chatId, UserState.STATE_START_RESPONSE);
            return response;
        }
        stateMap.put(chatId, UserState.STATE_DEFAULT);
        return response;

    }

    private String commandRequest(){
        return "введите токен";
    }

    private String commandResponse(Update update){

        if(TokenStatus.VALID_TOKEN.equals(createNewSandbox(update))){
            return "Ваш sandbox счет успешно создан и на него зачисленно 6 миллионов рублей";
        }
        else {
            return "Попробуйте ввести токен снова";
        }
    }

    private TokenStatus createNewSandbox(Update update){

        var chatId = update.getMessage().getChatId();
        var inputMessage = update.getMessage().getText();

        try {
            var accountId = commandServiceClient.createNewSandbox(inputMessage);

            System.out.println(accountId);

            TelegramUser newTelegramUser = TelegramUser
                    .builder()
                    .chatId(chatId)
                    .sandboxToken(inputMessage)
                    .sandboxId(accountId)
                    .build();

            saveTelegramUser(newTelegramUser);

        }catch (Exception e ){
            System.out.println(e.getMessage());
            return TokenStatus.INVALID_TOKEN;
        }

        return TokenStatus.VALID_TOKEN;
    }

    private void saveTelegramUser(TelegramUser user){
        telegramUserProducer.sendTelegramUser(user);
    }
//    private boolean checkUserExistInDB(long chatId){
//
//    }

}













