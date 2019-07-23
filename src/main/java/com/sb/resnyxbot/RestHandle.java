package com.sb.resnyxbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import resnyx.TgMethod;
import resnyx.model.Update;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/bot")
public class RestHandle {

    private static final Logger LOG = LoggerFactory.getLogger(RestHandle.class);
    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(5);

    private final ResnyxAnswers resnyxAnswers;

    @Autowired
    public RestHandle(ResnyxAnswers answers) {
        this.resnyxAnswers = answers;
    }

    @PostMapping("/{token}")
    public void incoming(@PathVariable String token, @RequestBody Update payload) {
        LOG.info(payload.toString());
        if (shouldAnswer(payload)) {
            THREAD_POOL.submit(() -> {
                try {
                    for (TgMethod method : resnyxAnswers.choose(token, payload.getMessage())) {
                        method.execute();
                    }
                } catch (IOException ex) {
                    LOG.warn(ex.getMessage(), ex);
                }
            });
        }
    }

    private boolean shouldAnswer(Update update) {
        String text = update.getMessage().getText();
        return text != null
                && (text.startsWith("@resnyx") || text.startsWith("/"));
    }
}
