package io.sandbox.telegram_bot;

import io.sandbox.api_database.JpaService;
import io.sandbox.api_tinkoff_invest.InvestApi;
import io.sandbox.command.Command;
import io.sandbox.command.RequestCommand;
import io.sandbox.command.ResponseCommand;
import io.sandbox.request_strategy.CompanyDataRequest;
import io.sandbox.request_strategy.PostOrderRequest;
import io.sandbox.request_strategy.StartRequest;
import io.sandbox.response_strategy.*;
import io.sandbox.kafka.TelegramUserProducer;
import io.sandbox.user_state.UserState;
import io.sandbox.utils.message.CommonMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;


@Component
public class CommandExecutor {

    private final TelegramUserProducer telegramUserProducer;
    private final InvestApi investApi;
    private final JpaService jpaService;

    @Autowired
    public CommandExecutor(TelegramUserProducer telegramUserProducer, InvestApi investApi, JpaService jpaService) {
        this.telegramUserProducer = telegramUserProducer;
        this.investApi = investApi;
        this.jpaService = jpaService;
    }

    public void executeCommand(Map<Long, UserState> userMap, Update update, TelegramBot telegramBot) {

        var chatId = update.getMessage().getChatId();
        var userState = userMap.get(chatId);

        Command command = null;

        switch (userState) {

            case STATE_START_REQUEST -> command = new RequestCommand(new StartRequest(jpaService));
            case STATE_START_RESPONSE -> command = new ResponseCommand(new StartResponse(telegramUserProducer, investApi));

            case STATE_POST_ORDER_REQUEST -> command = new RequestCommand(new PostOrderRequest());
            case STATE_POST_ORDER_RESPONSE -> command = new ResponseCommand(new PostOrderResponse(investApi));

            case STATE_COMPANY_DATA_REQUEST -> command = new RequestCommand(new CompanyDataRequest());
            case STATE_COMPANY_DATA_RESPONSE -> command = new ResponseCommand(new CompanyDataResponse(investApi));

            case STATE_PORTFOLIO -> command = new ResponseCommand(new PortfolioResponse(investApi));

            case STATE_OPERATIONS -> command = new ResponseCommand(new OperationsResponse(investApi));

            default -> waitForTextHandler(userMap, update, telegramBot);
        }

        if (command != null) {
            command.execute(update, userMap, telegramBot);
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

            default -> CommonMessage.sendDefaultMessage(update, telegramBot);
        }
        executeCommand(hashMap, update, telegramBot);
    }
}