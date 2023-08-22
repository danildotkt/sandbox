package io.sandbox.utils.message;

public class PostOrderMessage {

    public static String tickerPromptMessage() {
        return "Введите тикер компании \nНапример: Aqua 49";
    }

    public static String incorrectInputMessage() {
        return """
                Извините, но ваш ввод некорректен\s
                Пожалуйста, попробуйте снова
                Пример правильного формата: NVTK 139""";
    }


    public static String formatOrder(int quantity, String instrumentName, String executedPricePerStock) {
        return "Вы купили " + quantity + " акции " +
                instrumentName +
                " по цене " + executedPricePerStock + " ₽";
    }
}
