package io.sandbox.utils.message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StartMessageTest {

    @Test
    void testAlreadyRegisteredMessage() {
        String expected = "Вы уже зарегистрированы в sandbox";
        String actual = StartMessage.alreadyRegisteredMessage();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testWelcomeMessage() {
        String expected = "Добро пожаловать в песочницу для покупки акций!" +
                " Для продолжения, пожалуйста, введите токен Tinkoff invest  " +
                "по ссылке https://www.tinkoff.ru/invest/settings/api/ \n" +
                "Обратите внимание что вам нужно вставить токен именно для песочницы!!";
        String actual = StartMessage.welcomeMessage();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testSuccessCreateSandboxMessage() {
        String expected = "Ваш sandbox аккаунт успешно создан и на него зачислено 6 миллионов рублей";
        String actual = StartMessage.successCreateSandboxMessage();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testFailToCreateSandboxMessage() {
        String expected = "Не удалось создать sandbox аккаунт( \n Пожалуйста, проверьте правильность введенного токена и попробуйте еще раз";
        String actual = StartMessage.failToCreateSandboxMessage();
        Assertions.assertEquals(expected, actual);
    }
}