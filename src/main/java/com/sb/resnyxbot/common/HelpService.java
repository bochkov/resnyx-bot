package com.sb.resnyxbot.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import resnyx.TgMethod;
import resnyx.methods.message.SendMessage;
import resnyx.model.Message;

@Service
@ChooseScope("?")
@RequiredArgsConstructor
public final class HelpService implements ResnyxService {

    private final List<ResnyxService> services;

    @Override
    public List<TgMethod<Message>> answer(String token, Message msg) {
        List<String> text = new ArrayList<>();
        text.add("Доступные команды:");
        for (ResnyxService service : services) {
            Class<?> clz = service.getClass();
            ChooseScope ann = clz.getAnnotation(ChooseScope.class);
            if (ann != null && !ann.desc().isEmpty()) {
                text.add(
                        String.format("%s - %s", Arrays.toString(ann.value()), ann.desc())
                );
            }
        }
        if (text.size() > 1) {
            SendMessage sm = new SendMessage(token, msg.getChat().getId(), String.join("\n", text));
            sm.setDisablePreview(true);
            return Collections.singletonList(sm);
        } else {
            return Collections.emptyList();
        }
    }
}
