package io.sandbox.user_state;

import io.sandbox.api_database.JpaService;
import io.sandbox.api_tinkoff_invest.InvestApi;
import io.sandbox.command_request.CompanyDataRequest;
import io.sandbox.command_request.PostOrderRequest;
import io.sandbox.command_request.RequestStrategy;
import io.sandbox.command_request.StartRequest;
import io.sandbox.command_response.*;
import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.utils.MessageUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;


@Component
public class UserStateSwitcher {

    private final InvestApi investApi;
    private final JpaService jpaService;

    public UserStateSwitcher(InvestApi investApi, JpaService jpaService) {
        this.investApi = investApi;
        this.jpaService = jpaService;
    }

    public void userStateSwitch(Map<Long, UserState> userMap, Update update, TelegramBot telegramBot) {

        var chatId = update.getMessage().getChatId();
        var userState = userMap.get(chatId);

        RequestStrategy requestStrategy = null;
        ResponseStrategy responseStrategy = null;

        switch (userState) {

            case STATE_START_REQUEST -> requestStrategy = new StartRequest(jpaService);
            case STATE_START_RESPONSE -> responseStrategy = new StartResponse(investApi);

            case STATE_POST_ORDER_REQUEST -> requestStrategy = new PostOrderRequest();
            case STATE_POST_ORDER_RESPONSE -> responseStrategy = new PostOrderResponse(investApi);

            case STATE_COMPANY_DATA_REQUEST -> requestStrategy = new CompanyDataRequest();
            case STATE_COMPANY_DATA_RESPONSE -> responseStrategy = new CompanyDataResponse(investApi);

            case STATE_PORTFOLIO -> responseStrategy = new PortfolioResponse(investApi);

            case STATE_OPERATIONS -> responseStrategy = new OperationsResponse(investApi);

            default -> waitForTextHandler(userMap, update, telegramBot);
        }

        if (requestStrategy != null) {
            requestStrategy.sendRequest(update, userMap, telegramBot);
        } else if (responseStrategy != null) {
            responseStrategy.sendResponse(update, userMap, telegramBot);
        }
    }

    private void waitForTextHandler(Map<Long, UserState> hashMap, Update update, TelegramBot telegramBot) {

        var inputMessage = update.getMessage().getText();
        var chatId = update.getMessage().getChatId();

        hashMap.remove(chatId);

        switch (inputMessage) {

            case "/start" -> hashMap.put(chatId, UserState.STATE_START_REQUEST);

            case "/portfolio" -> hashMap.put(chatId, UserState.STATE_PORTFOLIO);

            case "/post_order" -> hashMap.put(chatId, UserState.STATE_POST_ORDER_REQUEST);

            case "/operations" -> hashMap.put(chatId, UserState.STATE_OPERATIONS);

            case "/company_data" -> hashMap.put(chatId, UserState.STATE_COMPANY_DATA_REQUEST);

            default -> MessageUtils.defaultMessage(update, telegramBot);
        }
        userStateSwitch(hashMap, update, telegramBot);
    }
}