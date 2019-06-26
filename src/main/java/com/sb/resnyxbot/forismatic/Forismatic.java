package com.sb.resnyxbot.forismatic;

import com.jcabi.http.request.JdkRequest;
import com.sb.resnyxbot.prop.PropRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import resnyx.methods.message.SendMessage;

import java.io.IOException;

@Service
public class Forismatic {

    private static final Logger LOG = LoggerFactory.getLogger(Forismatic.class);

    private final PropRepo propRepo;

    @Autowired
    public Forismatic(PropRepo propRepo) {
        this.propRepo = propRepo;
    }

    // second, minute, hour, day of month, month and day of week
    @Scheduled(cron = "0 0 0 * * *")
    public void send() {
        propRepo
                .findById("tg.token")
                .ifPresentOrElse(
                        tok -> propRepo
                                .findById("chat.auto.send")
                                .ifPresentOrElse(
                                        prop -> {
                                            for (String chatId : prop.getValue().split(";")) {
                                                try {
                                                    String text = String.format("Мудрость дня:%n%s", get());
                                                    new SendMessage(
                                                            tok.getValue(),
                                                            Long.valueOf(chatId),
                                                            text
                                                    ).execute();
                                                } catch (IOException ex) {
                                                    LOG.warn(ex.getMessage(), ex);
                                                }
                                            }
                                        },
                                        () -> LOG.info("chat.auto.send is empty. exit")
                                ),
                        () -> LOG.info("tg.token is empty. exit")
                );


    }

    public String get() {
        try {
            return new JdkRequest("http://api.forismatic.com/api/1.0/")
                    .uri()
                    .queryParam("method", "getQuote")
                    .queryParam("format", "text")
                    .queryParam("lang", "ru")
                    .back()
                    .fetch()
                    .body();
        } catch (IOException ex) {
            LOG.warn(ex.getMessage(), ex);
            return "не сегодня";
        }
    }
}
