package com.sb.resnyxbot.bot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sb.resnyxbot.config.TgBot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import resnyx.TgMethod;
import resnyx.messenger.general.Message;
import resnyx.updates.Update;

@Slf4j
@RestController
@RequestMapping("/bot")
@RequiredArgsConstructor
public final class BotApi {

    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(5);

    private final TgBot tgBot;
    private final ResnyxAnswers resnyxAnswers;

    @PostMapping("/{token}")
    public void incoming(@PathVariable String token, @RequestBody Update payload) {
        LOG.info(payload.toString());
        Message msg = getMessage(payload);
        if (shouldAnswer(msg)) {
            THREAD_POOL.submit(() -> {
                for (TgMethod method : resnyxAnswers.answerFor(msg)) {
                    tgBot.execute(method, token);
                }
            });
        }
    }

    private Message getMessage(Update update) {
        if (update.getEditedMessage() != null)
            return update.getEditedMessage();
        else if (update.getMessage() != null)
            return update.getMessage();
        else
            return null;
    }

    private boolean shouldAnswer(Message msg) {
        return msg != null && msg.getText() != null &&
                (msg.getText().startsWith("@resnyx") || msg.getText().startsWith("/"));
    }
}
