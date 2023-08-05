package io.sandbox.user_state;


import io.sandbox.command.*;
import io.sandbox.command_request.CompanyDataRequest;
import io.sandbox.command_request.PostOrderRequest;
import io.sandbox.command_request.StartRequest;
import io.sandbox.response.*;
import io.sandbox.telegrambot.TelegramBotService;
import io.sandbox.utils.MessageUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

@Component
public class UserStateSwitcher {

    public void userStateSwitch(HashMap<Long, UserState> hashMap, Update update, TelegramBotService telegramBotService) {

        var chatId = update.getMessage().getChatId();
        var userState = hashMap.get(chatId);

        switch (userState) {

            case STATE_START_REQUEST -> StartRequest.sendTelegramMessageRequest(update, hashMap, telegramBotService);
            case STATE_START_RESPONSE -> StartResponse.sendTelegramMessageResponse(update, hashMap, telegramBotService);

            case STATE_POST_ORDER_REQUEST -> PostOrderRequest.sendTelegramMessageRequest(update, hashMap, telegramBotService);
            case STATE_POST_ORDER_RESPONSE -> PostOrderResponse.sendTelegramMessageResponse(update, hashMap, telegramBotService);

            case STATE_PORTFOLIO -> PortfolioResponse.sendTelegramMessageResponse(update, hashMap, telegramBotService);

            case STATE_OPERATIONS -> OperationsResponse.sendTelegramMessageResponse(update, hashMap, telegramBotService);

            case STATE_COMPANY_DATA_REQUEST -> CompanyDataRequest.sendTelegramMessageRequest(update, hashMap, telegramBotService);
            case STATE_COMPANY_DATA_RESPONSE -> CompanyDataResponse.sendTelegramMessageResponse(update, hashMap, telegramBotService);

            default -> waitForTextHandler(hashMap, update, telegramBotService);
        }
    }

    private void waitForTextHandler(HashMap<Long, UserState> hashMap, Update update, TelegramBotService telegramBotService) {
        var inputMessage = update.getMessage().getText();
        var chatId = update.getMessage().getChatId();
        hashMap.remove(chatId);
        switch (inputMessage) {

            case "/start" -> {
                hashMap.put(chatId, UserState.STATE_START_REQUEST);
                userStateSwitch(hashMap, update, telegramBotService);
            }

            case "/portfolio" -> {
                hashMap.put(chatId, UserState.STATE_PORTFOLIO);
                userStateSwitch(hashMap, update, telegramBotService);
            }

            case "/post_order" -> {
                hashMap.put(chatId, UserState.STATE_POST_ORDER_REQUEST);
                userStateSwitch(hashMap, update, telegramBotService);
            }

            case "/operations" -> {
                hashMap.put(chatId, UserState.STATE_OPERATIONS);
                userStateSwitch(hashMap, update, telegramBotService);
            }

            case "/company_data" -> {
                hashMap.put(chatId, UserState.STATE_COMPANY_DATA_REQUEST);
                userStateSwitch(hashMap, update, telegramBotService);
            }

            default -> MessageUtils.defaultMessage(update, telegramBotService);
        }
    }
}








