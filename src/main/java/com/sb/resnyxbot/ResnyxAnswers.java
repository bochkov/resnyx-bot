package com.sb.resnyxbot;

import java.util.Collections;
import java.util.List;

import com.sb.resnyxbot.anekdot.Anekdot;
import com.sb.resnyxbot.auto.AutoServ;
import com.sb.resnyxbot.forismatic.Forismatic;
import com.sb.resnyxbot.qr.QrService;
import com.sb.resnyxbot.rutor.Rutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import resnyx.TgMethod;
import resnyx.methods.message.SendMessage;
import resnyx.model.Message;

@Component
@RequiredArgsConstructor
public final class ResnyxAnswers {

    private final Forismatic forismatic;
    private final Rutor rutor;
    private final QrService qrService;
    private final AutoServ autoServ;
    private final Anekdot anekdot;

    public List<TgMethod<Message>> choose(final String token, final Message msg) {
        final Long chatId = msg.getChat().getId();
        final String text = msg.getText().toLowerCase();
        if (text.contains("цитат")) {
            return forismatic.answer(token, msg);
        } else if (text.contains("qr")) {
            return qrService.answer(token, msg);
        } else if (text.contains("авто")) {
            return autoServ.answer(token, msg);
        } else if (text.contains("rutor")) {
            return rutor.answer(token, msg);
        } else if (text.contains("анек")) {
            return anekdot.answer(token, msg);
        } else {
            String txt = String.format("Your chat_id = %s", chatId);
            return Collections.singletonList(
                    new SendMessage(token, chatId, txt)
            );
        }
    }
}
