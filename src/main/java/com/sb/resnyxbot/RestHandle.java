package com.sb.resnyxbot;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import resnyx.TgMethod;
import resnyx.model.Update;

@Slf4j
@RestController
@RequestMapping("/bot")
@RequiredArgsConstructor
public class RestHandle {

    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(5);

    private final ResnyxAnswers resnyxAnswers;

    @PostMapping("/{token}")
    public void incoming(@PathVariable String token, @RequestBody Update payload) {
        String msg = payload.toString();
        LOG.info(msg);
        if (shouldAnswer(payload)) {
            THREAD_POOL.submit(() -> {
                try {
                    for (TgMethod<?> method : resnyxAnswers.choose(token, payload.getMessage())) {
                        method.execute();
                    }
                } catch (IOException ex) {
                    LOG.warn(ex.getMessage(), ex);
                }
            });
        }
    }

    private boolean shouldAnswer(Update update) {
        String text = fromMsg(update);
        return text != null
                && (text.startsWith("@resnyx") || text.startsWith("/"));
    }

    private String fromMsg(Update update) {
        if (update.getMessage() != null)
            return update.getMessage().getText();
        if (update.getEditedMessage() != null)
            return update.getEditedMessage().getText();
        return "";
    }
}
