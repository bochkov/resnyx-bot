package com.sb.resnyxbot.services.cbr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.sb.resnyxbot.bot.ChooseScope;
import com.sb.resnyxbot.bot.PushResnyx;
import com.sb.resnyxbot.services.cbr.model.CalcRange;
import com.sb.resnyxbot.services.cbr.service.CbrService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import resnyx.TgMethod;
import resnyx.messenger.general.Message;
import resnyx.messenger.general.SendMessage;

@Slf4j
@Service
@ChooseScope(value = "курс", desc = "Официальный курс валют")
@RequiredArgsConstructor
public final class CbrResnyx extends PushResnyx {

    private final CbrService cbrService;

    @Override
    public List<TgMethod> answer(Message msg) {
        String[] values = msg.getText().split("\s+");
        List<String> currencies = Arrays.asList(values).subList(1, values.length);
        if (currencies.isEmpty()) {
            currencies.add("USD");
            currencies.add("EUR");
        }
        List<CalcRange> calcRanges = cbrService.latestRange(currencies);
        List<TgMethod> methods = new ArrayList<>();
        String chatId = String.valueOf(msg.getChat().getId());
        for (CalcRange cr : calcRanges) {
            methods.add(new SendMessage(chatId, cr.asString()));
        }
        return methods;
    }

    @Override
    protected String pushText() {
        List<String> currencies = List.of("USD", "EUR");
        return cbrService.latestRange(currencies).stream()
                .map(CalcRange::asString)
                .collect(Collectors.joining("\n"));
    }
}
