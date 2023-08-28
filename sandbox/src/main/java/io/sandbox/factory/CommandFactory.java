package io.sandbox.factory;

import io.sandbox.api.database.JpaService;
import io.sandbox.api.tinkoff_invest.InvestApi;
import io.sandbox.kafka.TelegramUserProducer;
import io.sandbox.request.BuyStockRequest;
import io.sandbox.request.CommandRequest;
import io.sandbox.request.CompanyDataRequest;
import io.sandbox.request.StartRequest;
import io.sandbox.response.*;
import io.sandbox.user_state.UserState;
import org.springframework.stereotype.Service;

@Service
public class CommandFactory {

    private final JpaService jpaService;
    private final InvestApi investApi;
    private final TelegramUserProducer telegramUserProducer;

    public CommandFactory(JpaService jpaService, InvestApi investApi, TelegramUserProducer telegramUserProducer) {
        this.jpaService = jpaService;
        this.investApi = investApi;
        this.telegramUserProducer = telegramUserProducer;
    }

    public  CommandRequest createRequest(UserState userState) {
        return switch (userState) {
            case STATE_START_REQUEST -> new StartRequest(jpaService);
            case STATE_POST_ORDER_REQUEST -> new BuyStockRequest();
            case STATE_COMPANY_DATA_REQUEST -> new CompanyDataRequest();
            default -> throw new IllegalArgumentException("Invalid user state for request");
        };
    }

    public CommandResponse createResponse(UserState userState) {
        return switch (userState) {
            case STATE_START_RESPONSE -> new StartResponse(telegramUserProducer, investApi);
            case STATE_POST_ORDER_RESPONSE -> new BuyStockResponse(investApi);
            case STATE_COMPANY_DATA_RESPONSE -> new CompanyDataResponse(investApi);
            case STATE_PORTFOLIO_RESPONSE -> new PortfolioResponse(investApi);
            case STATE_OPERATIONS_RESPONSE -> new OperationsResponse(investApi);
            default -> throw new IllegalArgumentException("Invalid user state for response");
        };
    }
}
