package exKid.bot;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public class ExKid extends TelegramLongPollingBot {
    private static final String TOKEN = "1838076023:AAG4-0N9mLTa0C10F5B1FiMZOvlfI3jjKgo";
    private static final String USERNAME = "exkid_bot";

    public ExKid(DefaultBotOptions options) {
        super(options);
    }
    public String getBotToken() {return TOKEN;}
    public String getBotUsername() {return USERNAME;}

    public void onUpdateReceived(Update update) {
        if(update.getMessage()!=null && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            try {
                if(update.getMessage().getText().equals("Я люблю тебя"))
                    execute(new SendMessage(chatId, "Спасибо \uD83D\uDE09"));
                else
                    execute(new SendMessage(chatId, "heya"));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

}
