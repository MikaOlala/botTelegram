package exKid.service;

import exKid.bot.ExKid;
import exKid.command.Command;
import exKid.command.ParsedCommand;
import exKid.command.Parser;
import exKid.handler.AbstractHandler;
import exKid.handler.DefaultHandler;
import exKid.handler.SystemHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MessageReciever implements Runnable {
    private ExKid exKid;
    private final int WAIT_FOR_NEW_MESSAGE_DELAY = 1000;
    private Parser parser;

    public MessageReciever(ExKid exKid) {
        this.exKid = exKid;
        parser = new Parser();
    }

    public void run() {
        while(true) {
            for(Object object = exKid.receiveQueue.poll(); object!=null; object = exKid.receiveQueue.poll()) {
                analyze(object);
            }
            try {
                Thread.sleep(WAIT_FOR_NEW_MESSAGE_DELAY);
            } catch (InterruptedException e) {
                System.out.println("Can't interrupt. " + e);
                return;
            }
        }
    }

    public void analyze(Object object) {
        if(object instanceof Update) {
            Update update = (Update) object;
            analyzeForUpdateType(update);
        } else System.out.println("Can't operate the type");
    }
    public void analyzeForUpdateType(Update update) {
        Long chatId = update.getMessage().getChatId();
        String inputText = update.getMessage().getText();

        ParsedCommand parsedCommand = parser.getParsedCommand(inputText);
        AbstractHandler handlerForCommand = getHandlerForCommand(parsedCommand.getCommand());

        String operationResult = handlerForCommand.operate(chatId.toString(), parsedCommand, update);

        if(!operationResult.equals("")) {
            SendMessage message = new SendMessage();
            message.setChatId(chatId);
            message.setText(operationResult);
            exKid.sendQueue.add(message);
        }
    }

    private AbstractHandler getHandlerForCommand (Command command) {
        if(command == null) {
            return new DefaultHandler(exKid);
        }
        switch(command) {
            case START:
            case EUR:
            case USD:
            case RUB:
                return new SystemHandler(exKid);
            default:
                return new DefaultHandler(exKid);
        }
    }
}
