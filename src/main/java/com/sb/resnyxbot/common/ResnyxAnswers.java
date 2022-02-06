package com.sb.resnyxbot.common;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import resnyx.TgMethod;
import resnyx.methods.message.SendMessage;
import resnyx.model.Message;

@Slf4j
@Component
@RequiredArgsConstructor
public final class ResnyxAnswers {

    private final Map<String, ResnyxService> services;

    public List<TgMethod<Message>> choose(final String token, final Message msg) {
        for (ResnyxService service : services.values()) {
            Class<?> clz = service.getClass();
            if (clz.isAnnotationPresent(ChooseScope.class)) {
                ChooseScope scope = clz.getAnnotation(ChooseScope.class);
                for (String str : scope.value()) {
                    if (msg.getText().toLowerCase().contains(str.toLowerCase())) {
                        LOG.info("choosed service = {}", service);
                        return service.answer(token, msg);
                    }
                }
            }
        }
        Long chatId = msg.getChat().getId();
        String txt = String.format("Your chat_id = %s", chatId);
        return Collections.singletonList(
                new SendMessage(token, chatId, txt)
        );
    }
}
