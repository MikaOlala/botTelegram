package exKid.handler;

import exKid.bot.ExKid;
import exKid.command.ParsedCommand;
import org.telegram.telegrambots.meta.api.objects.Update;

public class DefaultHandler extends AbstractHandler{
    public DefaultHandler(ExKid exKid) {
        super(exKid);
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        return "";
    }
}
