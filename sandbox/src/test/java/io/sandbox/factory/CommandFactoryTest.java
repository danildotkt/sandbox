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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandFactoryTest {

    @Mock
    private JpaService jpaService;
    @Mock
    private InvestApi investApi;
    @Mock
    private TelegramUserProducer telegramUserProducer;
    @InjectMocks
    private CommandFactory commandFactory;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateRequest() {
        CommandRequest startRequest = commandFactory.createRequest(UserState.STATE_START_REQUEST);
        assertTrue(startRequest instanceof StartRequest);

        CommandRequest buyStockRequest = commandFactory.createRequest(UserState.STATE_POST_ORDER_REQUEST);
        assertTrue(buyStockRequest instanceof BuyStockRequest);

        CommandRequest companyDataRequest = commandFactory.createRequest(UserState.STATE_COMPANY_DATA_REQUEST);
        assertTrue(companyDataRequest instanceof CompanyDataRequest);

    }

    @Test
    public void testCreateResponse() {
        // Проверяем создание команды StartResponse
        CommandResponse startResponse = commandFactory.createResponse(UserState.STATE_START_RESPONSE);
        assertTrue(startResponse instanceof StartResponse);

        // Проверяем создание команды BuyStockResponse
        CommandResponse buyStockResponse = commandFactory.createResponse(UserState.STATE_POST_ORDER_RESPONSE);
        assertTrue(buyStockResponse instanceof BuyStockResponse);

        // Проверяем создание команды CompanyDataResponse
        CommandResponse companyDataResponse = commandFactory.createResponse(UserState.STATE_COMPANY_DATA_RESPONSE);
        assertTrue(companyDataResponse instanceof CompanyDataResponse);

        // Проверяем создание команды PortfolioResponse
        CommandResponse portfolioResponse = commandFactory.createResponse(UserState.STATE_PORTFOLIO_RESPONSE);
        assertTrue(portfolioResponse instanceof PortfolioResponse);

        // Проверяем создание команды OperationsResponse
        CommandResponse operationsResponse = commandFactory.createResponse(UserState.STATE_OPERATIONS_RESPONSE);
        assertTrue(operationsResponse instanceof OperationsResponse);
        }
}