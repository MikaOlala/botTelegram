package exKid.bot;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;


public class ExKid extends TelegramLongPollingBot {
    private static final String TOKEN = "1838076023:AAG4-0N9mLTa0C10F5B1FiMZOvlfI3jjKgo";
    private static final String USERNAME = "exkid_bot";

    public ExKid(DefaultBotOptions options) {
        super(options);
    }
    public String getBotToken() {return TOKEN;}
    public String getBotUsername() {return USERNAME;}
    public ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    public final Queue<Object> sendQueue = new ConcurrentLinkedQueue<Object>();
    public final Queue<Object> receiveQueue = new ConcurrentLinkedQueue<Object>();

    public void onUpdateReceived(Update update) {
//        SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
//        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        sendMessage.setText(getKeyboard());
        getKeyboard();
//        try {
//            execute(sendMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
        receiveQueue.add(update);
    }

    private void getKeyboard() {
        ArrayList<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
        KeyboardRow eur = new KeyboardRow();
        KeyboardRow usd = new KeyboardRow();
        KeyboardRow rub = new KeyboardRow();

        replyKeyboardMarkup.setSelective(false)
                .setResizeKeyboard(true)
                .setOneTimeKeyboard(true);

        eur.add("EUR");
        usd.add("USD");
        rub.add("RUB");

        keyboard.add(eur);
        keyboard.add(usd);
        keyboard.add(rub);

        replyKeyboardMarkup.setKeyboard(keyboard);
    }

}
