package com.sb.resnyxbot.services.anekdot;

import java.util.List;

import com.sb.resnyxbot.bot.ChooseScope;
import com.sb.resnyxbot.bot.PushResnyx;
import com.sb.resnyxbot.services.anekdot.service.AnekdotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import resnyx.TgMethod;
import resnyx.common.SendOptions;
import resnyx.messenger.general.Message;
import resnyx.messenger.general.SendMessage;

@Slf4j
@Service
@RequiredArgsConstructor
@ChooseScope(value = "анек", desc = "Случайный анекдот от https://baneks.ru/")
public final class AnekdotResnyx extends PushResnyx {

    private final AnekdotService anekdotService;

    @Override
    protected void configure(SendMessage answer) {
        SendOptions opts = new SendOptions();
        opts.setDisableNotification(true);
        answer.setSendOptions(opts);
        answer.setDisableWebPagePreview(true);
    }

    @Override
    public List<TgMethod> answer(Message msg) {
        String chatId = String.valueOf(msg.getChat().getId());
        return List.of(new SendMessage(chatId, anekdotService.random().getText()));
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
