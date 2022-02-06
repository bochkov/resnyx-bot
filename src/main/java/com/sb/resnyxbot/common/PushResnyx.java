package com.sb.resnyxbot.common;

import java.io.IOException;

import com.sb.resnyxbot.prop.Prop;
import com.sb.resnyxbot.prop.PropRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import resnyx.methods.message.SendMessage;

@Slf4j
public abstract class PushResnyx implements PushService {

    @Autowired
    private PropRepo propRepo;

    protected abstract String pushText();

    protected void configure(SendMessage answer) {
        // do nothing
    }

    @Override
    public void push() {
        String msg = pushText();
        push(msg);
    }

    @Override
    public void push(String msg) {
        propRepo
                .findById("tg.token")
                .ifPresentOrElse(
                        tok -> propRepo
                                .findById("chat.auto.send")
                                .ifPresentOrElse(
                                        prop -> push(prop, tok.getValue(), msg),
                                        () -> LOG.info("chat.auto.send is empty. exit")
                                ),
                        () -> LOG.info("tg.token is empty. exit")
                );
    }

    private void push(Prop chatId, String token, String msg) {
        for (String chId : chatId.getValue().split(";")) {
            try {
                SendMessage answer = new SendMessage(
                        token,
                        Long.valueOf(chId),
                        msg
                );
                configure(answer);
                answer.execute();
            } catch (IOException ex) {
                LOG.warn(ex.getMessage(), ex);
            }
        }
    }
}
