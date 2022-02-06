package com.sb.resnyxbot.forismatic;

import java.util.Collections;
import java.util.List;

import com.sb.resnyxbot.common.ChooseScope;
import com.sb.resnyxbot.common.PushResnyx;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import resnyx.TgMethod;
import resnyx.methods.message.SendMessage;
import resnyx.model.Message;

@Slf4j
@Service
@RequiredArgsConstructor
@ChooseScope("цитат")
public class ForismaticResnyx extends PushResnyx {

    private final CiteService citeService;

    @Override
    public List<TgMethod<Message>> answer(String token, Message msg) {
        String cite = citeService.get().asString();
        return Collections.singletonList(
                new SendMessage(token, msg.getChat().getId(), cite)
        );
    }

    @Override
    protected String pushText() {
        Cite cite = citeService.get();
        return String.format("Мудрость дня:%n%s", cite.asString());
    }
}
