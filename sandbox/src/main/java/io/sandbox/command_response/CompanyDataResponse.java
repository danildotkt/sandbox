package io.sandbox.command_response;

import io.sandbox.api_tinkoff_invest.TinkoffInvestApiClient;
import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;


public class CompanyDataResponse implements ResponseStrategy {

    private final TinkoffInvestApiClient tinkoffInvestApiClient;

    public CompanyDataResponse(TinkoffInvestApiClient tinkoffInvestApiClient) {
        this.tinkoffInvestApiClient = tinkoffInvestApiClient;
    }

    public void sendResponse(Update update, Map<Long, UserState> hashMap, TelegramBot telegramBot) {
        telegramBot.sendMessage(update, switchStateResponse(update, hashMap));
    }

    private String switchStateResponse(Update update, Map<Long, UserState> hashMap){

        var response = response(update);
        if(response.matches("^https.+")){
            hashMap.remove(update.getMessage().getChatId());
        }
        return response;
    }

    private String response(Update update){

        var ticker = update.getMessage().getText();
        var chatId = update.getMessage().getChatId();

        try {
            tinkoffInvestApiClient.getInstrumentByTicker(chatId, ticker);
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
