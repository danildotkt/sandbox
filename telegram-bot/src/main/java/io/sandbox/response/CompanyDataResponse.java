package io.sandbox.response;

import io.sandbox.grpc_client.CommandClient;
import io.sandbox.telegrambot.TelegramBotService;
import io.sandbox.user_state.UserState;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

public class CompanyDataResponse {

    public static void sendTelegramMessageResponse(Update update, HashMap<Long, UserState> hashMap, TelegramBotService telegramBotService) {
        telegramBotService.sendMessage(update, switchStateResponse(update, hashMap));
    }

    private static String switchStateResponse(Update update, HashMap<Long, UserState> hashMap){

        var response = response(update);
        if(response.matches("^https.+")){
            hashMap.remove(update.getMessage().getChatId());
        }
        return response;
    }

    private static String response(Update update){

        var ticker = update.getMessage().getText();
        var chatId = update.getMessage().getChatId();

        try {
            CommandClient.getInstrumentName(chatId, ticker);
        }
        catch(Exception e){
            return "Компании c тикером \""+ticker+"\" не существует, попробуйте снова \nНапример : gazp";
        }
        if(ticker.equals("SNGSP") || ticker.equals("SBERP") ||ticker.equals("TATNP") ||ticker.equals("BANEP")){
            var InputMessageForPreferredShares = ticker.substring(0,ticker.length()-1);
            return "https://smart-lab.ru/q/"+InputMessageForPreferredShares+"/f/y/";
        }

        return "https://smart-lab.ru/q/"+ticker.toUpperCase()+"/f/y/";
    }
}

