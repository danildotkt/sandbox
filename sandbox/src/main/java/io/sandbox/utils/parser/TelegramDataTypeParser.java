package io.sandbox.utils.parser;

import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramDataTypeParser {

    public static int getQuantityFromUpdate(Update update) {
        var inputMessage = update.getMessage().getText();
        var inputMessageArr = inputMessage.split(" ", 2);
        return Integer.parseInt(inputMessageArr[1]);
    }

    public static String getTickerFromUpdate(Update update) {
        var inputMessage = update.getMessage().getText();
        var inputMessageArr = inputMessage.split(" ", 2);
        return inputMessageArr[0];
    }
}
