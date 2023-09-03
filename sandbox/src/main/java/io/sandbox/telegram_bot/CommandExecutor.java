package io.sandbox.telegram_bot;

import io.sandbox.factory.CommandFactory;
import io.sandbox.request.CommandRequest;
import io.sandbox.response.CommandResponse;
import io.sandbox.user_state.UserState;
import io.sandbox.utils.message.CommonMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;


@Component
public class CommandExecutor {

    private final CommandFactory commandFactory;

    @Autowired
    public CommandExecutor(CommandFactory commandFactory) {
        this.commandFactory = commandFactory;
    }

    public void executeCommand(Map<Long, UserState> stateMap, Update update, TelegramBot telegramBot) {
        var chatId = update.getMessage().getChatId();
        var userState = stateMap.get(chatId);

        if (userState.isRequest()) {
            handleRequest(stateMap, update, telegramBot, userState);
        } else if (userState.isResponse()) {
            handleResponse(stateMap, update, telegramBot, userState);
        } else {
            defaultStateHandler(stateMap, update, telegramBot);
        }
    }

    private void handleRequest(Map<Long, UserState> stateMap, Update update, TelegramBot telegramBot, UserState userState) {
        CommandRequest request = commandFactory.createRequest(userState);
        request.sendRequest(update, stateMap, telegramBot);
    }

    private void handleResponse(Map<Long, UserState> stateMap, Update update, TelegramBot telegramBot, UserState userState) {
        CommandResponse response = commandFactory.createResponse(userState);
        response.sendResponse(update, stateMap, telegramBot);
    }

    private void defaultStateHandler(Map<Long, UserState> stateMap, Update update, TelegramBot telegramBot) {

        var inputMessage = update.getMessage().getText();
        var chatId = update.getMessage().getChatId();

        stateMap.remove(chatId);

        switch (inputMessage) {
            case "/start" -> {
                stateMap.put(chatId, UserState.STATE_START_REQUEST);
                executeCommand(stateMap, update ,telegramBot);
            }
            case "/portfolio" ->{
                stateMap.put(chatId, UserState.STATE_PORTFOLIO_RESPONSE);
                executeCommand(stateMap, update ,telegramBot);
            }

            case "/buy_stock" -> {
                stateMap.put(chatId, UserState.STATE_BUY_STOCK_REQUEST);
                executeCommand(stateMap, update ,telegramBot);
            }

            case "/operations" -> {
                stateMap.put(chatId, UserState.STATE_OPERATIONS_RESPONSE);
                executeCommand(stateMap, update, telegramBot);
            }
            case "/company_data" -> {
                stateMap.put(chatId, UserState.STATE_COMPANY_DATA_REQUEST);
                executeCommand(stateMap, update, telegramBot);
            }
            default -> CommonMessage.sendDefaultMessage(update, telegramBot);
        }
    }
}