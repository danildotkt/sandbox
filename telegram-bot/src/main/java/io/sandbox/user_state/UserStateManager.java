package io.sandbox.user_state;


import io.sandbox.command.CompanyData;
import io.sandbox.command.Portfolio;
import io.sandbox.command.PostOrder;
import io.sandbox.command.Start;
import io.sandbox.telegrambot.TelegramBot;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserStateManager {

    private final Start start;
//    private final PostOrder postOrder;
//    private final Operation operation;
//    private final Portfolio portfolio;
//    private final CompanyData companyData;

    @Autowired
    public UserStateManager(Start start) {
        this.start = start;
//        this.postOrder = postOrder;
//        this.operation = operation;
//        this.portfolio = portfolio;
//        this.companyData = companyData;
    }

    public void userStateManager(HashMap<Long, UserState> hashMap, Update update){

        var chatId = update.getMessage().getChatId();
        var userState = hashMap.get(chatId);

        switch (userState){

            case STATE_START_REQUEST ->  start.handleRequestState(hashMap,update);

            case STATE_START_RESPONSE -> start.handleResponseState(hashMap, update);

//            case STATE_POST_ORDER_REQUEST -> postOrder.requestStateHandler(hashMap, update, telegramBotService);
//
//            case STATE_POST_ORDER_RESPONSE -> postOrder.responseStateHandler(hashMap, update, telegramBotService);
//
//            case STATE_PORTFOLIO -> portfolio.responseStateHandler(hashMap, update, telegramBotService);
//
//            case STATE_OPERATIONS -> operation.responseStateHandler(hashMap, update, telegramBotService);
//
//            case STATE_COMPANY_DATA_REQUEST -> companyData.requestStateHandler(hashMap, update, telegramBotService);
//
////            case STATE_COMPANY_DATA_TYPE -> companyData.requestKeyboard(hashMap, update, telegramBotService);
//
//            case STATE_COMPANY_DATA_RESPONSE -> companyData.responseStateHandler(hashMap, update, telegramBotService);
//
//            default -> waitForTextHandler(hashMap, update, telegramBotService);
        }
    }

    private void waitForTextHandler(HashMap<Long, UserState> hashMap, Update update) {
        var inputMessage = update.getMessage().getText();
        var chatId = update.getMessage().getChatId();
        hashMap.remove(chatId);
        switch (inputMessage) {

            case "/start" -> {
                hashMap.put(chatId, UserState.STATE_START_REQUEST);
                userStateManager(hashMap, update, telegramBotService);
            }

//            case "/portfolio" -> {
//                hashMap.put(chatId, UserState.STATE_PORTFOLIO);
//                userStateManager(hashMap, update, telegramBotService);
//            }
//
//            case "/post_order" -> {
//                hashMap.put(chatId, UserState.STATE_POST_ORDER_REQUEST);
//                userStateManager(hashMap, update, telegramBotService);
//            }
//
//            case "/operations" -> {
//                hashMap.put(chatId, UserState.STATE_OPERATIONS);
//                userStateManager(hashMap, update, telegramBotService);
//            }
//
//            case "/company_data" -> {
//                hashMap.put(chatId, UserState.STATE_COMPANY_DATA_REQUEST);
//                userStateManager(hashMap, update, telegramBotService);
//            }
//
//            default -> telegramBotService.sendMessage(MessageUtils.defaultMessage(update));

        }
    }


}































