package com.sb.resnyxbot.cbr;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
@ChooseScope(value = "курс", desc = "Официальный курс валют")
@RequiredArgsConstructor
public final class CbrResnyx extends PushResnyx {

    private final CbrService cbrService;

    @Override
    public List<TgMethod<Message>> answer(String token, Message msg) {
        // TODO get code from msg
        List<CalcRange> calcRanges = cbrService.latestRange();
        List<TgMethod<Message>> methods = new ArrayList<>();
        for (CalcRange cr : calcRanges) {
            methods.add(new SendMessage(token, msg.getChat().getId(), cr.asString()));
        }
        return methods;
    }

    @Override
    protected String pushText() {
        return cbrService.latestRange().stream()
                .map(CalcRange::asString)
                .collect(Collectors.joining("\n"));
    }
}
