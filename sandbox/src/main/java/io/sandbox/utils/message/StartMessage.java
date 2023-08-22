package io.sandbox.utils.message;

public class StartMessage {

    public static String alreadyRegisteredMessage() {
        return "Вы уже зарегистрированы в sandbox";
    }

    public static String welcomeMessage() {
        return "Добро пожаловать в песочницу для покупки акций!" +
                " Для продолжения, пожалуйста, введите токен Tinkoff invest  " +
                "по ссылке https://www.tinkoff.ru/invest/settings/api/ \n" +
                "Обратите внимание что вам нужно вставить токен именно для песочницы!!";
    }



    public static String successCreateSandboxMessage() {
        return "Ваш sandbox аккаунт успешно создан и на него зачислено 6 миллионов рублей";
    }

    public static String failToCreateSandboxMessage(){
        return "Не удалось создать sandbox аккаунт( \n Пожалуйста, проверьте правильность введенного токена и попробуйте еще раз";
    }
}
