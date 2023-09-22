package com.sb.resnyxbot.bot;

import com.sb.resnyxbot.config.Prop;
import com.sb.resnyxbot.config.PropRepo;
import com.sb.resnyxbot.config.TgBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import resnyx.messenger.general.SendMessage;

@Slf4j
public abstract class PushResnyx implements PushService {

    @Autowired
    private PropRepo propRepo;
    @Autowired
    private TgBot tgBot;

    protected abstract String pushText();

    protected void configure(SendMessage answer) {
        // do nothing
    }

    @Override
    public void push() {
        String msg = pushText();
        push(msg);
    }

    protected String chatPropName() {
        return "chat.auto.send";
    }

    @Override
    public void push(String msg) {
        propRepo
                .findById("tg.token")
                .ifPresentOrElse(
                        tok -> propRepo
                                .findById(chatPropName())
                                .ifPresentOrElse(
                                        prop -> push(prop, tok.getValue(), msg),
                                        () -> LOG.info("{} is empty. exit", chatPropName())
                                ),
                        () -> LOG.info("tg.token is empty. exit")
                );
    }

    private void push(Prop chatId, String token, String msg) {
        for (String chId : chatId.getValue().split(";")) {
            try {
                SendMessage answer = new SendMessage(chId, msg);
                configure(answer);
                tgBot.execute(answer, token);
            } catch (Exception ex) {
                LOG.warn(ex.getMessage(), ex);
            }
        }
    }
}
