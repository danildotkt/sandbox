package io.sandbox.utils;

import io.sandbox.telegram_bot.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;


public class MessageUtils {
    public static void defaultMessage(Update update, TelegramBot telegramBot){
        var text =  "Для взаимодействия со мной вы можете " +
                "использовать следущие команды :\n" +
                "/portfolio - просмотр портфеля \n" +
                "/post_order - покупка акций \n" +
                "/operations - список операций \n" +
                "/company_data - финансовые отчеты компании";

        telegramBot.sendMessage(update , text);
    }
}
