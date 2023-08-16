package io.sandbox.user_state;

import io.sandbox.command_request.CompanyDataRequest;
import io.sandbox.command_request.PostOrderRequest;
import io.sandbox.command_request.StartRequest;
import io.sandbox.command_response.*;
import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.utils.MessageUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.ConcurrentHashMap;


@Component
public class UserStateSwitcher {

    public void userStateSwitch(ConcurrentHashMap<Long, UserState> hashMap, Update update, TelegramBot telegramBot) {

        var chatId = update.getMessage().getChatId();
        var userState = hashMap.get(chatId);

        switch (userState) {

            case STATE_START_REQUEST -> StartRequest.sendMessage(update, hashMap, telegramBot);
            case STATE_START_RESPONSE -> StartResponse.sendMessage(update, hashMap, telegramBot);

            case STATE_POST_ORDER_REQUEST -> PostOrderRequest.sendMessage(update, hashMap, telegramBot);
            case STATE_POST_ORDER_RESPONSE -> PostOrderResponse.sendMessage(update, hashMap, telegramBot);

            case STATE_PORTFOLIO -> PortfolioResponse.sendMessage(update, hashMap, telegramBot);

            case STATE_OPERATIONS -> OperationsResponse.sendMessage(update, hashMap, telegramBot);

            case STATE_COMPANY_DATA_REQUEST -> CompanyDataRequest.sendMessage(update, hashMap, telegramBot);
            case STATE_COMPANY_DATA_RESPONSE -> CompanyDataResponse.sendMessage(update, hashMap, telegramBot);

            default -> waitForTextHandler(hashMap, update, telegramBot);
        }
    }

    private void waitForTextHandler(ConcurrentHashMap<Long, UserState> hashMap, Update update, TelegramBot telegramBot) {

        var inputMessage = update.getMessage().getText();
        var chatId = update.getMessage().getChatId();

        hashMap.remove(chatId);

        switch (inputMessage) {
            case "/start" -> {
                hashMap.put(chatId, UserState.STATE_START_REQUEST);
                userStateSwitch(hashMap, update, telegramBot);
            }
            case "/portfolio" -> {
                hashMap.put(chatId, UserState.STATE_PORTFOLIO);
                userStateSwitch(hashMap, update, telegramBot);
            }
            case "/post_order" -> {
                hashMap.put(chatId, UserState.STATE_POST_ORDER_REQUEST);
                userStateSwitch(hashMap, update, telegramBot);
            }
            case "/operations" -> {
                hashMap.put(chatId, UserState.STATE_OPERATIONS);
                userStateSwitch(hashMap, update, telegramBot);
            }
            case "/company_data" -> {
                hashMap.put(chatId, UserState.STATE_COMPANY_DATA_REQUEST);
                userStateSwitch(hashMap, update, telegramBot);
            }
            default -> MessageUtils.defaultMessage(update, telegramBot);
        }
    }
}