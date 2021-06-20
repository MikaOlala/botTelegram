package exKid;

import exKid.bot.ExKid;
import exKid.service.MessageReciever;
import exKid.service.MessageSender;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class Main {
    public static void main(String[] args) {

        ApiContextInitializer.init();

        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);
        botOptions.setProxyHost("localhost");
        botOptions.setProxyPort(9150);

        ExKid exKid = new ExKid(botOptions);
        MessageReciever messageReciever = new MessageReciever(exKid);
        MessageSender messageSender = new MessageSender(exKid);

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

        try {
            telegramBotsApi.registerBot(exKid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread receiver = new Thread(messageReciever);
        receiver.setDaemon(true);
        receiver.setName("MsgReceiver");
        receiver.start();

        Thread sender = new Thread(messageSender);
        sender.setDaemon(true);
        sender.setName("MsgSender");
        sender.start();

        sendStartReport(exKid);
    }

    private static void sendStartReport(ExKid exKid) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Запустился");
        exKid.sendQueue.add(sendMessage);
    }
}
