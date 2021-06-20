package exKid.handler;

import exKid.bot.ExKid;
import exKid.command.ParsedCommand;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractHandler {
    ExKid exKid;

    AbstractHandler(ExKid exKid) {
        this.exKid = exKid;
    }

    public abstract String operate(String chatId, ParsedCommand parsedCommand, Update update);
}
