package io.sandbox.utils.message;

public class CompanyDataMessage {

    public static String tickerPrompt() {
        return "Пожалуйста, введите тикер компании \nНапример: Rosn";
    }

    public static String tickerNotFoundMessage(String ticker) {
        return "К сожалению, компания с тикером \"" + ticker + "\" не найдена \n" +
                "Пожалуйста, попробуйте снова \n" +
                "Например: Gazp";
    }

    public static String smartLabUrlMessage(String ticker) {
        if (ticker.equals("SNGSP") || ticker.equals("SBERP") || ticker.equals("TATNP") || ticker.equals("BANEP")) {
            String inputMessageForPreferredShares = ticker.substring(0, ticker.length() - 1);
            return "https://smart-lab.ru/q/" + inputMessageForPreferredShares + "/f/y/";
        } else {
            return "https://smart-lab.ru/q/" + ticker.toUpperCase() + "/f/y/";
        }
    }
}
