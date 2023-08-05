package io.sandbox.utils;

import io.sandbox.telegram_bot.TelegramBotService;
import org.telegram.telegrambots.meta.api.objects.Update;


public class MessageUtils {
    public static void defaultMessage(Update update, TelegramBotService telegramBotService){
        var text =  "Для взаимодействия со мной вы можете " +
                "использовать следущие команды :\n" +
                "/portfolio - просмотр портфеля \n" +
                "/post_order - выставления заявки \n" +
                "/operations - список операций \n" +
                "/company_data - информация компании";

        telegramBotService.sendMessage(update , text);
    }
}
