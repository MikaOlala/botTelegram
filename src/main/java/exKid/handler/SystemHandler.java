package exKid.handler;

import exKid.bot.ExKid;
import exKid.command.Command;
import exKid.command.ParsedCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class SystemHandler extends AbstractHandler {
    public SystemHandler(ExKid exKid) {
        super(exKid);
    }

    @Override
    public String operate(String chatId, ParsedCommand parsedCommand, Update update) {
        Command command = parsedCommand.getCommand();

        switch (command) {
            case START:
                exKid.sendQueue.add(getMessageStart(chatId));
                break;
            case EUR:
                return "Команда Евро \uD83E\uDD11";
            case USD:
                return "Команда Доллар \uD83E\uDD14";
            case RUB:
                return "Команда Рубль \uD83D\uDE31";
            default:
                return "hey";
        }
        return "";
    }

    private SendMessage getMessageStart(String chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableMarkdown(true);

        StringBuilder text = new StringBuilder();
        text.append("Привет. Я ExKid! \n");
        text.append("Пока что я мало, что умею... Но вы можете поиграть с моими полуготовыми командами \uD83D\uDE1D");
        sendMessage.setText(text.toString());
        return sendMessage;
    }
}
