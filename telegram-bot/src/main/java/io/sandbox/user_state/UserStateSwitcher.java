package io.sandbox.user_state;


import io.sandbox.command.*;
import io.sandbox.telegrambot.TelegramBotService;
import io.sandbox.utils.MessageUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;

@Component
public class UserStateSwitcher {

    private final Start start;
    private final PostOrder postOrder;
//    private final Operation operation;
//    private final Portfolio portfolio;
//    private final CompanyData companyData;


    public UserStateSwitcher(Start start, PostOrder postOrder, Operation operation, Portfolio portfolio, CompanyData companyData) {
        this.start = start;
        this.postOrder = postOrder;
//        this.operation = operation;
//        this.portfolio = portfolio;
//        this.companyData = companyData;
    }

    public void userStateSwitch(HashMap<Long, UserState> hashMap, Update update, TelegramBotService telegramBotService) {

        var chatId = update.getMessage().getChatId();
        var userState = hashMap.get(chatId);

        switch (userState) {

            case STATE_START_REQUEST -> start.sendTelegramMessageRequest(update, hashMap, telegramBotService);
            case STATE_START_RESPONSE -> start.sendTelegramMessageResponse(update, hashMap, telegramBotService);

            case STATE_POST_ORDER_REQUEST -> postOrder.sendTelegramMessageRequest(update, hashMap, telegramBotService);
            case STATE_POST_ORDER_RESPONSE -> postOrder.sendTelegramMessageResponse(update, hashMap, telegramBotService);
//
//            case STATE_PORTFOLIO -> portfolio.responseStateHandler(hashMap, update, telegramBotService);
//
//            case STATE_OPERATIONS -> operation.responseStateHandler(hashMap, update, telegramBotService);
//
//            case STATE_COMPANY_DATA_REQUEST -> companyData.requestStateHandler(hashMap, update, telegramBotService);
//            case STATE_COMPANY_DATA_RESPONSE -> companyData.responseStateHandler(hashMap, update, telegramBotService);

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

//            case "/portfolio" -> {
//                hashMap.put(chatId, UserState.STATE_PORTFOLIO);
//                userStateSwitch(hashMap, update, telegramBotService);
//            }
//
            case "/post_order" -> {
                hashMap.put(chatId, UserState.STATE_POST_ORDER_REQUEST);
                userStateSwitch(hashMap, update, telegramBotService);
            }
//
//            case "/operations" -> {
//                hashMap.put(chatId, UserState.STATE_OPERATIONS);
//                userStateSwitch(hashMap, update, telegramBotService);
//            }
//
//            case "/company_data" -> {
//                hashMap.put(chatId, UserState.STATE_COMPANY_DATA_REQUEST);
//                userStateSwitch(hashMap, update, telegramBotService);
//            }

            default -> MessageUtils.defaultMessage(update, telegramBotService);
        }
    }

}








