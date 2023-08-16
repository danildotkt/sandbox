package io.sandbox.command_response;

import io.sandbox.api_tinkoff_invest.TinkoffApi;
import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import io.sandbox.utils.TinkoffDataTypeParser;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.piapi.contract.v1.PortfolioPosition;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class PortfolioResponse {

    public static void sendMessage(Update update, ConcurrentHashMap<Long, UserState> hashMap, TelegramBot telegramBot) {

        var positionsList = switchStateResponse(update, hashMap);
        var chatId = update.getMessage().getChatId();
        var profileName = getProfileName(update);

        telegramBot.sendMessage(update, profileName);

        for (var position : positionsList) {
            var figi = position.getFigi();

            var instrument = TinkoffApi.getInstrumentByFigi(chatId,  figi);

            if(instrument.getName().equals("Российский рубль")){
                continue;
            }

            String info = instrument.getName()
                    + "\n"
                    + position.getQuantity().getUnits()
                    + " акций "
                    + "\n"
                    + TinkoffDataTypeParser.MoneyValueToDouble(position.getAveragePositionPrice())
                    + "  ->  "
                    + TinkoffDataTypeParser.MoneyValueToDouble(position.getAveragePositionPrice()) + " ₽";

            telegramBot.sendMessage(update, info);
        }
    }
    private static String getProfileName(Update update) {
        var chat = update.getMessage().getChat();
        return chat.getFirstName() + " " + ifLastNameIsNull(chat.getLastName()) + "  \uD83E\uDD13";
    }

    private static String ifLastNameIsNull(String lastName) {
        if (lastName == null) {
            return "";
        }
        return lastName;
    }

    private static List<PortfolioPosition> switchStateResponse(Update update, ConcurrentHashMap<Long, UserState> hashMap){
        var chatId = update.getMessage().getChatId();
        hashMap.remove(chatId);
        return getPortfolioPositionList(update);
    }

    private static List<PortfolioPosition> getPortfolioPositionList(Update update){
        return TinkoffApi.sandboxPortfolio(update.getMessage().getChatId());
    }

}
