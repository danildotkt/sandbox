package io.sandbox.command_response;

import io.sandbox.api_tinkoff_invest.TinkoffInvestApiClient;
import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import io.sandbox.utils.DecimalUtils;
import io.sandbox.utils.TinkoffDataTypeParser;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.piapi.contract.v1.PortfolioPosition;

import java.util.List;
import java.util.Map;


public class PortfolioResponse implements ResponseStrategy {

    private final TinkoffInvestApiClient tinkoffInvestApiClient;

    public PortfolioResponse(TinkoffInvestApiClient tinkoffInvestApiClient) {
        this.tinkoffInvestApiClient = tinkoffInvestApiClient;
    }

    public void sendResponse(Update update, Map<Long, UserState> hashMap, TelegramBot telegramBot) {

        var positionsList = switchStateResponse(update, hashMap);
        var chatId = update.getMessage().getChatId();

        sendPortfolioName(update ,telegramBot);

        for (var position : positionsList) {
            var figi = position.getFigi();

            var instrument = tinkoffInvestApiClient.getInstrumentByFigi(chatId,  figi);

            if(instrument.getName().equals("Российский рубль")){
                continue;
            }

            var quantity = position.getQuantity().getUnits();
            var averagePositionPrice = TinkoffDataTypeParser.MoneyValueToDouble(position.getAveragePositionPrice());
            var currentPositionPrice = TinkoffDataTypeParser.MoneyValueToDouble(position.getCurrentPrice());
            var percentChangePositionPrice = ((currentPositionPrice - averagePositionPrice) / averagePositionPrice) * 100;
            var profit = (currentPositionPrice - averagePositionPrice) * quantity;
            var allPositionsPrice = averagePositionPrice*quantity;

            String info = instrument.getName()
                    + "\n"
                    + quantity
                    + " акций "
                    + "\n"
                    + averagePositionPrice
                    + "  ->  "
                    + currentPositionPrice + " ₽"
                    + "\n"
                    + formatProfit(percentChangePositionPrice) + " % "
                    + formatProfit(profit) + " ₽"
                    + "\n"
                    + allPositionsPrice + " ₽";

            telegramBot.sendMessage(update, info);
        }
    }
    private String formatProfit(double number) {
        var roundNumber = DecimalUtils.roundForDoubleValue(number);
        if (number >= 0) {
            return "+" + roundNumber;
        } else {
            return roundNumber;
        }
    }
    private void sendPortfolioName(Update update, TelegramBot telegramBot) {
        var chat = update.getMessage().getChat();
        var portfolioName = chat.getFirstName() + " " + ifLastNameIsNull(chat.getLastName()) + "  \uD83E\uDD13";
        telegramBot.sendMessage(update, portfolioName);
    }
    private String ifLastNameIsNull(String lastName) {
        if (lastName == null) {
            return "";
        }
        return lastName;
    }

    private List<PortfolioPosition> switchStateResponse(Update update, Map<Long, UserState> hashMap){
        var chatId = update.getMessage().getChatId();
        hashMap.remove(chatId);
        return getPortfolioPositionList(update);
    }

    private List<PortfolioPosition> getPortfolioPositionList(Update update){
        return tinkoffInvestApiClient.sandboxPortfolio(update.getMessage().getChatId());
    }

}
