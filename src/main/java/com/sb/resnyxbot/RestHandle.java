package com.sb.resnyxbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import resnyx.methods.message.SendMessage;
import resnyx.model.Update;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/bot")
public class RestHandle {

    private static final Logger LOG = LoggerFactory.getLogger(RestHandle.class);
    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(5);

    @PostMapping("/{token}")
    public void incoming(@PathVariable String token, @RequestBody Update payload) {
        LOG.info(payload.toString());
        final Long chatId = payload.getMessage().getChat().getId();
        final String text = payload.getMessage().getText();
        if (text != null && (text.startsWith("@resnyx") || text.startsWith("/"))) {
            THREAD_POOL.submit(() -> {
                try {
                    new SendMessage(
                            token,
                            chatId,
                            String.format("Your chatId = %s", chatId)
                    ).execute();
                } catch (IOException ex) {
                    LOG.warn(ex.getMessage());
                }
            });
        }
    }
}
