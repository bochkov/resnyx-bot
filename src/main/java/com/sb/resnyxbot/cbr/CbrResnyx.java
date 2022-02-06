package com.sb.resnyxbot.cbr;

import java.util.Collections;
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
@ChooseScope("курс")
@RequiredArgsConstructor
public final class CbrResnyx extends PushResnyx {

    private final CbrService cbrService;

    @Override
    public List<TgMethod<Message>> answer(String token, Message msg) {
        return Collections.singletonList(
                new SendMessage(
                        token, msg.getChat().getId(), cbrService.latestRates().toString()
                )
        );
    }

    @Override
    protected String pushText() {
        return cbrService.latestRange().stream()
                .map(CalcRange::asString)
                .collect(Collectors.joining("\n"));
    }
}
