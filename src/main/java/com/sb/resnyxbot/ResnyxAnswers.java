package com.sb.resnyxbot;

import com.sb.resnyxbot.forismatic.Forismatic;
import com.sb.resnyxbot.qr.QrService;
import com.sb.resnyxbot.rutor.Rutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import resnyx.TgMethod;
import resnyx.methods.message.SendMessage;
import resnyx.model.Message;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public final class ResnyxAnswers {

    private final Forismatic forismatic;
    private final Rutor rutor;
    private final QrService qrService;

    public List<TgMethod<Message>> choose(final String token, final Message msg) {
        final Long chatId = msg.getChat().getId();
        final String text = msg.getText().toLowerCase();
        if (text.contains("цитат")) {
            String txt = forismatic.get();
            return Collections.singletonList(
                    new SendMessage(token, chatId, txt)
            );
        } else if (text.contains("qr")) {
            return Collections.emptyList();
        } else if (text.contains("rutor")) {
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
