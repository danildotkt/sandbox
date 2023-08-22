package io.sandbox.response_strategy;

import io.sandbox.api_tinkoff_invest.InvestApi;
import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import io.sandbox.utils.message.CompanyDataMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;


public class CompanyDataResponse implements ResponseStrategy {

    private final InvestApi investApi;

    public CompanyDataResponse(InvestApi investApi) {
        this.investApi = investApi;
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
            investApi.getInstrumentByTicker(chatId, ticker);
        }
        catch(Exception e){
            return CompanyDataMessage.tickerNotFoundMessage(ticker);
        }
        return CompanyDataMessage.smartLabUrlMessage(ticker);
    }
}
