package io.sandbox.command_response;

import io.sandbox.api_tinkoff_invest.InvestApi;
import io.sandbox.entity.TelegramUser;
import io.sandbox.kafka.TelegramUserProducer;
import io.sandbox.telegram_bot.TelegramBot;
import io.sandbox.user_state.UserState;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;

public class StartResponse implements ResponseStrategy {

    private final InvestApi investApi;

    public StartResponse(InvestApi investApi) {
        this.investApi = investApi;
    }

    public void sendResponse(Update update, Map<Long, UserState> stateMap, TelegramBot telegramBot){
        telegramBot.sendMessage(update, switchStateResponse(update, stateMap));
    }

    private String switchStateResponse(Update update, Map<Long, UserState> stateMap){
        long chatId = update.getMessage().getChatId();
        var response = createNewSandbox(update);
        stateMap.remove(chatId);
        if(response.equals("Попробуйте ввести токен снова")){
            stateMap.put(chatId, UserState.STATE_START_RESPONSE);
            return response;
        }
        stateMap.put(chatId, UserState.STATE_DEFAULT);
        return response;

    }

    private String createNewSandbox(Update update){

        var chatId = update.getMessage().getChatId();
        var inputMessage = update.getMessage().getText();
        String accountId = null;

        try {
            accountId = investApi.createNewSandbox(inputMessage);
        }catch (Exception e ){
            System.out.println(e.getMessage());
            return "Попробуйте ввести токен снова";
        }

        TelegramUser newTelegramUser = TelegramUser
                .builder()
                .chatId(chatId)
                .sandboxToken(inputMessage)
                .accountId(accountId)
                .build();

        saveTelegramUser(newTelegramUser);

        return "Ваш sandbox счет успешно создан и на него зачисленно 6 миллионов рублей";
    }

    private void saveTelegramUser(TelegramUser user){
        var telegramUserProducer = new TelegramUserProducer();
        telegramUserProducer.sendTelegramUser(user);
    }
}
