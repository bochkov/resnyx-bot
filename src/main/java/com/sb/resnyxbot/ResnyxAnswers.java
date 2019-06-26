package com.sb.resnyxbot;

import com.sb.resnyxbot.forismatic.Forismatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import resnyx.TgMethod;
import resnyx.methods.message.SendMessage;
import resnyx.model.Message;

@Component
public final class ResnyxAnswers {

    private final Forismatic forismatic;

    @Autowired
    public ResnyxAnswers(Forismatic forismatic) {
        this.forismatic = forismatic;
    }

    public TgMethod choose(final String token, final Message msg) {
        final Long chatId = msg.getChat().getId();
        if (msg.getText().toLowerCase().contains("цитат")) {
            String txt = forismatic.get();
            return new SendMessage(token, chatId, txt);
        } else {
            String txt = String.format("Your chat_id = %s", chatId);
            return new SendMessage(token, chatId, txt);
        }
    }
}
