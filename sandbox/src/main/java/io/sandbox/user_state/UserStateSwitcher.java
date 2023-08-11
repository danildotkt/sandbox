package io.sandbox.user_state;

import io.sandbox.command_request.CompanyDataRequest;
import io.sandbox.command_request.PostOrderRequest;
import io.sandbox.command_request.StartRequest;
import io.sandbox.command_response.CompanyDataResponse;
import io.sandbox.command_response.PostOrderResponse;
import io.sandbox.command_response.StartResponse;
import io.sandbox.telegram_bot.TelegramBotService;
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

            case STATE_START_REQUEST -> StartRequest.sendMessage(update, hashMap, telegramBotService);
            case STATE_START_RESPONSE -> StartResponse.sendMessage(update, hashMap, telegramBotService);

            case STATE_POST_ORDER_REQUEST -> PostOrderRequest.sendMessage(update, hashMap, telegramBotService);
            case STATE_POST_ORDER_RESPONSE -> PostOrderResponse.sendMessage(update, hashMap, telegramBotService);

//            case STATE_PORTFOLIO -> PortfolioResponse.sendMessage(update, hashMap, telegramBotService);
//
//            case STATE_OPERATIONS -> OperationsResponse.sendMessage(update, hashMap, telegramBotService);

            case STATE_COMPANY_DATA_REQUEST -> CompanyDataRequest.sendMessage(update, hashMap, telegramBotService);
            case STATE_COMPANY_DATA_RESPONSE -> CompanyDataResponse.sendMessage(update, hashMap, telegramBotService);

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