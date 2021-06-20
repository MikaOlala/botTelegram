package exKid.handler;

import exKid.bot.ExKid;
import exKid.command.Command;
import exKid.command.ParsedCommand;
import exKid.parsePage.DayRate;
import exKid.parsePage.Rate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.util.ArrayList;

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
                exKid.sendQueue.add(prepareMessage(chatId, "eur"));
                break;
            case USD:
                exKid.sendQueue.add(prepareMessage(chatId, "usd"));
                break;
            case RUB:
                exKid.sendQueue.add(prepareMessage(chatId, "rub"));
                break;
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

    private SendMessage prepareMessage(String chatId, String parameter) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableMarkdown(true);

        StringBuilder text = new StringBuilder();
        Rate object = new Rate();

        text.append("\uD83D\uDDD3 Курс ");
        if(parameter.equals("usd")) {
            text.append("доллара");
            object.setCurrentTask("usd");
        }
        else if(parameter.equals("eur")) {
            text.append("евро");
            object.setCurrentTask("eur");
        }
        else if(parameter.equals("rub")) {
            text.append("рубля");
            object.setCurrentTask("rub");
        }
        text.append(" за последние 10 дней: \n");
        
        try {
            object.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<DayRate> dayRates = object.getDayRates();

        for(DayRate dr : dayRates) {
            text.append(dr.getDate())
                    .append(" --- ")
                    .append(dr.getRate())
                    .append("₸ --- ");
            if(dr.getChange() < 0)
                text.append("\uD83D\uDD34 "); // красный
            else if(dr.getChange() > 0)
                text.append("\uD83D\uDFE2 +"); // зеленый

            text.append(dr.getChange())
                    .append("\n");
        }
        sendMessage.setText(text.toString());
        return sendMessage;
    }
}
