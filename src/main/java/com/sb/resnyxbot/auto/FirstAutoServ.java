package com.sb.resnyxbot.auto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import resnyx.TgMethod;
import resnyx.methods.message.SendMessage;
import resnyx.model.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public final class FirstAutoServ implements AutoServ {

    private static final Pattern DIGITS = Pattern.compile("\\d+");

    private final CodeRepo codes;
    private final RegionRepo regions;

    @Override
    public Region findRegionByCode(String code) {
        log.info("searching by code = '{}'", code);
        return codes.findByValue(code).getRegion();
    }

    @Override
    public List<Region> findRegionByName(String name) {
        log.info("searching by name = '{}'", name);
        return regions.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<TgMethod<Message>> answer(String token, Message msg) {
        String text = msg.getText();
        Matcher digits = DIGITS.matcher(text);
        List<Region> regs = digits.find() ?
                Collections.singletonList(findRegionByCode(digits.group())) :
                findRegionByName(text.substring(text.indexOf(' ') + 1));
        return (regs == null || regs.isEmpty()) ?
                Collections.singletonList(
                        new SendMessage(token, msg.getChat().getId(), "не смог(")
                ) :
                messages(regs, token, msg.getChat().getId());
    }

    private List<TgMethod<Message>> messages(List<Region> regions, String token, Long chatId) {
        List<TgMethod<Message>> messages = new ArrayList<>();
        for (Region region : regions) {
            String ans = String.format(
                    "%s = %s",
                    region.getName(),
                    region.getCodes()
                            .stream()
                            .map(Code::getValue)
                            .collect(Collectors.joining(", "))
            );
            messages.add(new SendMessage(token, chatId, ans));
        }
        return messages;
    }
}
