package com.sb.resnyxbot.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.sb.resnyxbot.bot.ChooseScope;
import com.sb.resnyxbot.bot.ResnyxService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import resnyx.TgMethod;
import resnyx.messenger.general.Message;
import resnyx.messenger.general.SendMessage;

@Service
@ChooseScope("?")
@RequiredArgsConstructor
public final class HelpService implements ResnyxService {

    private final List<ResnyxService> services;

    @Override
    public List<TgMethod> answer(Message msg) {
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
            String chatId = String.valueOf(msg.getChat().getId());
            SendMessage sm = new SendMessage(chatId, String.join("\n", text));
            sm.setDisableWebPagePreview(true);
            return Collections.singletonList(sm);
        } else {
            return Collections.emptyList();
        }
    }
}
