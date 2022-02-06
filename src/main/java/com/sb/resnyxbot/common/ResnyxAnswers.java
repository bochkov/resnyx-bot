package com.sb.resnyxbot.common;

import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import resnyx.TgMethod;
import resnyx.model.Message;

@Slf4j
@Component
@RequiredArgsConstructor
public final class ResnyxAnswers {

    private final ResnyxResolver resnyxResolver;

    public List<TgMethod<Message>> choose(final String token, final Message msg) {
        ResnyxService service = resnyxResolver.resolve(msg.getText());
        if (service == null) {
            LOG.info("CHAT ID = {}", msg.getChat().getId());
            return Collections.emptyList();
        } else {
            return service.answer(token, msg);
        }
    }
}
