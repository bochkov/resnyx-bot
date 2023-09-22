package com.sb.resnyxbot.bot;

import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import resnyx.TgMethod;
import resnyx.messenger.general.Message;

@Slf4j
@Component
@RequiredArgsConstructor
public final class ResnyxAnswers {

    private final ResnyxResolver resnyxResolver;

    public List<TgMethod> answerFor(final Message msg) {
        ResnyxService service = resnyxResolver.resolve(msg.getText());
        if (service != null) {
            return service.answer(msg);
        } else {
            LOG.info("CHAT ID = {}", msg.getChat().getId());
            return Collections.emptyList();
        }
    }
}
