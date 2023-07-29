package io.sandbox.command;

import io.sandbox.user_state.UserState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

@Component
public class Start {

    public void sendTelegramMessageRequest(){

    }

    public void sendTelegramMessageResponse(){

    }

    public String setStateRequest(Update update, HashMap<Long, UserState> stateMap){
        long chatId = update.getMessage().getChatId();
        stateMap.remove(chatId);
        stateMap.put(chatId, UserState.STATE_START_RESPONSE);
        return commandRequest();

    }
    public String setStateResponse(Update update, HashMap<Long, UserState> stateMap){
        long chatId = update.getMessage().getChatId();
        stateMap.remove(chatId);
        stateMap.put(chatId, UserState.STATE_DEFAULT);

        return null;
    }

    private String commandRequest(){
        return "введите токен";
    }

    private String commandResponse(Update update){
        return createNewSandboxAccount(update);
    }

    private String createNewSandboxAccount(Update update){



        return "meow=3";
    }

}













