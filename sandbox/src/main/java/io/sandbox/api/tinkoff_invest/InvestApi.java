package io.sandbox.api.tinkoff_invest;

import ru.tinkoff.piapi.contract.v1.*;

import java.util.List;


public interface InvestApi {

    String createNewSandbox(String token);

    PostOrderResponse postOrderBuyMarket(long chatId, String ticker, String quantity);

    List<PortfolioPosition> sandboxPortfolio(long chatId);

    List<Operation> sandboxOperations(long chatId);

    Share getInstrumentByTicker(long chatId, String ticker);

    Instrument getInstrumentByFigi(long chatId, String figi);

}
