package exKid.service;

import exKid.bot.ExKid;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Message;


public class MessageSender implements Runnable{
    private final ExKid exKid;
    private final int SENDER_SLEEP_TIME = 1000;

    public MessageSender (ExKid exkid) {
        this.exKid = exkid;
    }

    public void run() {
        try {
            while(true) {
                for(Object object = exKid.sendQueue.poll(); object!=null; object = exKid.sendQueue.poll()) {
                    send(object);
                }

                Thread.sleep(SENDER_SLEEP_TIME);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void send(Object object) {
        try {
            MessageType messageType = messageType(object);
            switch (messageType) {
                case EXECUTE:
                    BotApiMethod<Message> message = (BotApiMethod<Message>) object;
                    exKid.execute(message);
                    break;
                case STICKER:
//                    SendSticker sendSticker = (SendSticker) object;
//                    exKid.sendSticker(sendSticker);
                    break;
                default:
                    System.out.println("Can't detect the type");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private MessageType messageType(Object object) {
        if(object instanceof SendSticker) return MessageType.STICKER;
        if(object instanceof BotApiMethod) return MessageType.EXECUTE;
        return MessageType.NOT_DETECTED;
    }

    enum MessageType {
        EXECUTE,
        STICKER,
        NOT_DETECTED
    }
}
