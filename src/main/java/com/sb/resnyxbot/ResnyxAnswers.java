package com.sb.resnyxbot;

import com.sb.resnyxbot.forismatic.Forismatic;
import com.sb.resnyxbot.rutor.Rutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import resnyx.TgMethod;
import resnyx.methods.message.SendMessage;
import resnyx.model.Message;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public final class ResnyxAnswers {

    private final Forismatic forismatic;
    private final Rutor rutor;

    @Autowired
    public ResnyxAnswers(Forismatic forismatic, Rutor rutor) {
        this.forismatic = forismatic;
        this.rutor = rutor;
    }

    public List<TgMethod> choose(final String token, final Message msg) {
        final Long chatId = msg.getChat().getId();
        if (msg.getText().toLowerCase().contains("цитат")) {
            String txt = forismatic.get();
            return Collections.singletonList(
                    new SendMessage(token, chatId, txt)
            );
        } else if (msg.getText().toLowerCase().contains("rutor")) {
            try {
                return rutor
                        .fetch(msg.getText())
                        .toTgMethods(token, chatId);
            } catch (IOException ex) {
                return Collections.singletonList(
                        new SendMessage(token, chatId, ex.getMessage())
                );
            }
        } else {
            String txt = String.format("Your chat_id = %s", chatId);
            return Collections.singletonList(
                    new SendMessage(token, chatId, txt)
            );
        }
    }
}
