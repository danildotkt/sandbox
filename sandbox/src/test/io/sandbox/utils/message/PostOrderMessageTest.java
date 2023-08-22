package io.sandbox.utils.message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PostOrderMessageTest {

    @Test
    void testTickerPromptMessage() {
        String expected = "Введите тикер компании \nНапример: Aqua 49";
        String actual = PostOrderMessage.tickerPromptMessage();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testIncorrectInputMessage() {
        String expected = """
                Извините, но ваш ввод некорректен\s
                Пожалуйста, попробуйте снова
                Пример правильного формата: NVTK 139""";
        String actual = PostOrderMessage.incorrectInputMessage();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testFormatOrder() {
        int quantity = 10;
        String instrumentName = "AAPL";
        String executedPricePerStock = "150.50";

        String expected = "Вы купили 10 акции AAPL по цене 150.50 ₽";
        String actual = PostOrderMessage.formatOrder(quantity, instrumentName, executedPricePerStock);
        Assertions.assertEquals(expected, actual);
    }
}