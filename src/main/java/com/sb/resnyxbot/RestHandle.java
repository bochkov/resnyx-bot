package com.sb.resnyxbot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import resnyx.methods.message.SendMessage;
import resnyx.model.Update;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController("/bot")
public final class RestHandle {

    private static final Logger LOG = LoggerFactory.getLogger(RestHandle.class);
    private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(5);

    @PostMapping("/{token}")
    public void incoming(@PathVariable String token, @RequestBody Update payload) {
        THREAD_POOL.submit(() -> {
            LOG.info(payload.toString());
            Long chatId = payload.getMessage().getChat().getId();
            String text = payload.getMessage().getText();
            if (text.startsWith("@resnyx")) {
                SendMessage msg = new SendMessage();
                msg.setToken(token);
                msg.setChatId(chatId);
                msg.setText("Your chatId = " + chatId);
                try {
                    msg.execute();
                } catch (IOException ex) {
                    LOG.warn(ex.getMessage());
                }
            }
        });
    }
}
