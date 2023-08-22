package io.sandbox.utils.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompanyDataMessageTest {

    @Test
    void testTickerPrompt() {
        String expected = "Пожалуйста, введите тикер компании \nНапример: Rosn";
        String actual = CompanyDataMessage.tickerPrompt();
        assertEquals(expected, actual);
    }

    @Test
     void testTickerNotFoundMessage() {
        String ticker = "Gazp";
        String expected = "К сожалению, компания с тикером \"" + ticker + "\" не найдена \n" +
                "Пожалуйста, попробуйте снова \n" +
                "Например: Gazp";
        String actual = CompanyDataMessage.tickerNotFoundMessage(ticker);
        assertEquals(expected, actual);
    }

    @Test
     void testSmartLabUrlMessage() {
        String ticker = "SNGSP";
        String expected = "https://smart-lab.ru/q/SNGS/f/y/";
        String actual = CompanyDataMessage.smartLabUrlMessage(ticker);
        assertEquals(expected, actual);
    }

    @Test
     void testSmartLabUrlMessageWithPreferredShares() {
        String ticker = "SNGSP";
        String expected = "https://smart-lab.ru/q/SNGS/f/y/";
        String actual = CompanyDataMessage.smartLabUrlMessage(ticker);
        assertEquals(expected, actual);
    }
}