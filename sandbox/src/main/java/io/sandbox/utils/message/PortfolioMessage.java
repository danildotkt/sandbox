package io.sandbox.utils.message;

import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.utils.parser.DecimalParser;
import io.sandbox.utils.parser.TinkoffDataTypeParser;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.piapi.contract.v1.Instrument;
import ru.tinkoff.piapi.contract.v1.PortfolioPosition;

public class PortfolioMessage {

    public static String positionInfoMessage(PortfolioPosition position, Instrument instrument) {
        var quantity = position.getQuantity().getUnits();
        var averagePositionPrice = TinkoffDataTypeParser.MoneyValueToDouble(position.getAveragePositionPrice());
        var currentPositionPrice = TinkoffDataTypeParser.MoneyValueToDouble(position.getCurrentPrice());
        var percentChangePositionPrice = ((currentPositionPrice - averagePositionPrice) / averagePositionPrice) * 100;
        var profit = (currentPositionPrice - averagePositionPrice) * quantity;
        var allPositionsPrice = averagePositionPrice * quantity;

        return instrument.getName()
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
    }

    private static String formatProfit(double number) {
        var roundNumber = DecimalParser.roundForDoubleValue(number);
        if (number >= 0) {
            return "+" + roundNumber;
        } else {
            return roundNumber;
        }
    }

    public static void sendPortfolioName(Update update, TelegramBot telegramBot) {
        var chat = update.getMessage().getChat();
        var portfolioName = chat.getFirstName() + " " + ifLastNameIsNull(chat.getLastName()) + "  \uD83E\uDD13";
        telegramBot.sendMessage(update, portfolioName);
    }

    private static String ifLastNameIsNull(String lastName) {
        if (lastName == null) {
            return "";
        }
        return lastName;
    }
}
