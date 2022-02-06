package com.sb.resnyxbot.anekdot;

import java.util.Collections;
import java.util.List;

import com.sb.resnyxbot.common.ChooseScope;
import com.sb.resnyxbot.common.PushResnyx;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import resnyx.TgMethod;
import resnyx.methods.message.SendMessage;
import resnyx.model.Message;

@Slf4j
@Service
@RequiredArgsConstructor
@ChooseScope("анек")
public final class AnekdotResnyx extends PushResnyx {

    private final AnekdotService anekdotService;

    @Override
    public List<TgMethod<Message>> answer(String token, Message msg) {
        return Collections.singletonList(
                new SendMessage(token, msg.getChat().getId(), anekdotService.random().getText())
        );
    }

    @Override
    protected String pushText() {
        return String.format("Анекдот дня:%n%s", anekdotService.random().getText());
    }

    @Scheduled(cron = "0 0 6 * * *")
    @Override
    public void push() {
        super.push();
    }
}
