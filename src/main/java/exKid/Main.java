package exKid;

import exKid.bot.ExKid;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;

public class Main {
    public static void main(String[] args) {

//        System.getProperties().put("proxySet", "true");
//        System.getProperties().put("socksProxyHost", "127.0.0.1");
//        System.getProperties().put("socksProxyPort", "9150");

        ApiContextInitializer.init();

        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        botOptions.setProxyHost("localhost");
        botOptions.setProxyPort(9150);

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(new ExKid(botOptions));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
