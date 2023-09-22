package com.sb.resnyxbot.services.forismatic;

import java.util.List;

import com.sb.resnyxbot.bot.ChooseScope;
import com.sb.resnyxbot.bot.PushResnyx;
import com.sb.resnyxbot.services.forismatic.service.CiteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import resnyx.TgMethod;
import resnyx.messenger.general.Message;
import resnyx.messenger.general.SendMessage;

@Slf4j
@Service
@RequiredArgsConstructor
@ChooseScope(value = "цитат", desc = "Случайная цитата от https://www.forismatic.com/")
public final class ForismaticResnyx extends PushResnyx {

    private final CiteService citeService;

    @Override
    public List<TgMethod> answer(Message msg) {
        String cite = citeService.get().asString();
        String chatId = String.valueOf(msg.getChat().getId());
        return List.of(new SendMessage(chatId, cite));
    }

    @Override
    protected String pushText() {
        return String.format("Мудрость дня:%n%s", citeService.get().asString());
    }
}
