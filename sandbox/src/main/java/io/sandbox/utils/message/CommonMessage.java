package io.sandbox.utils.message;

import io.sandbox.telegram_bot.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;


public class CommonMessage {
    public static void sendDefaultMessage(Update update, TelegramBot telegramBot){
        var text = """
                Для взаимодействия со мной вы можете использовать следующие команды :
                /portfolio - просмотр портфеля\s
                /post_order - покупка акций\s
                /operations - список операций\s
                /company_data - финансовые отчеты компании""";

        telegramBot.sendMessage(update , text);
    }
}
