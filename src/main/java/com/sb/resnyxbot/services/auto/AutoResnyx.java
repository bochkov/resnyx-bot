package com.sb.resnyxbot.services.auto;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.sb.resnyxbot.bot.ChooseScope;
import com.sb.resnyxbot.bot.ResnyxService;
import com.sb.resnyxbot.services.auto.model.Code;
import com.sb.resnyxbot.services.auto.model.Region;
import com.sb.resnyxbot.services.auto.service.AutoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import resnyx.TgMethod;
import resnyx.messenger.general.Message;
import resnyx.messenger.general.SendMessage;

@Slf4j
@Service
@RequiredArgsConstructor
@ChooseScope(value = {"avto", "авто"}, desc = "Автомобильные коды регионов РФ")
public final class AutoResnyx implements ResnyxService {

    private static final Pattern DIGITS = Pattern.compile("\\d+");

    private final AutoService autoService;

    @Override
    public List<TgMethod> answer(Message msg) {
        String chatId = String.valueOf(msg.getChat().getId());
        String text = msg.getText();
        Matcher digits = DIGITS.matcher(text);
        List<Region> regs = digits.find() ?
                List.of(autoService.findRegionByCode(digits.group())) :
                autoService.findRegionByName(text.substring(text.indexOf(' ') + 1));
        return (regs == null || regs.isEmpty()) ?
                List.of(new SendMessage(chatId, "не смог(")) :
                messages(chatId, regs);
    }

    private List<TgMethod> messages(String chatId, List<Region> regions) {
        List<TgMethod> messages = new ArrayList<>();
        for (Region region : regions) {
            String ans = String.format(
                    "%s = %s",
                    region.getName(),
                    region.getCodes()
                            .stream()
                            .map(Code::getVal)
                            .collect(Collectors.joining(", "))
            );
            messages.add(new SendMessage(chatId, ans));
        }
        return messages;
    }
}
