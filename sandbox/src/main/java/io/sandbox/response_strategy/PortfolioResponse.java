package io.sandbox.response_strategy;

import io.sandbox.api_tinkoff_invest.InvestApi;
import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import io.sandbox.utils.message.PortfolioMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.piapi.contract.v1.PortfolioPosition;

import java.util.List;
import java.util.Map;


public class PortfolioResponse implements ResponseStrategy {

    private final InvestApi investApi;

    public PortfolioResponse(InvestApi investApi) {
        this.investApi = investApi;
    }


    public void sendResponse(Update update, Map<Long, UserState> hashMap, TelegramBot telegramBot) {
        var positionsList = switchStateResponse(update, hashMap);

         PortfolioMessage.sendPortfolioName(update, telegramBot);

        for (var position : positionsList) {
           sendPositionInfo(update, position, telegramBot);
        }
    }
    private void sendPositionInfo(Update update, PortfolioPosition position, TelegramBot telegramBot) {
        var figi = position.getFigi();
        var chatId = update.getMessage().getChatId();
        var instrument = investApi.getInstrumentByFigi(chatId, figi);

        if (instrument.getName().equals("Российский рубль")) {
            return;
        }
        String info = PortfolioMessage.positionInfoMessage(position, instrument);
        telegramBot.sendMessage(update, info);
    }

    private List<PortfolioPosition> switchStateResponse(Update update, Map<Long, UserState> hashMap){
        var chatId = update.getMessage().getChatId();
        hashMap.remove(chatId);
        return getPortfolioPositionList(update);
    }

    private List<PortfolioPosition> getPortfolioPositionList(Update update){
        return investApi.sandboxPortfolio(update.getMessage().getChatId());
    }

}
