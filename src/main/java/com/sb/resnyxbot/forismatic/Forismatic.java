package com.sb.resnyxbot.forismatic;

import com.jcabi.http.Response;
import com.jcabi.http.request.JdkRequest;
import com.sb.resnyxbot.prop.PropRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import resnyx.methods.message.SendMessage;

import java.io.IOException;
import java.net.HttpURLConnection;

@Service
public class Forismatic {

    private static final Logger LOG = LoggerFactory.getLogger(Forismatic.class);

    private final PropRepo propRepo;

    @Autowired
    public Forismatic(PropRepo propRepo) {
        this.propRepo = propRepo;
    }

    // second, minute, hour, day of month, month and day of week
    @Scheduled(cron = "0 0 6 * * *")
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
            Response resp = new JdkRequest("http://api.forismatic.com/api/1.0/")
                    .uri()
                    .queryParam("method", "getQuote")
                    .queryParam("format", "text")
                    .queryParam("lang", "ru")
                    .back()
                    .fetch();
            if (resp.status() != HttpURLConnection.HTTP_OK)
                throw new IOException(String.valueOf(resp.status()));
            return resp.body();
        } catch (IOException ex) {
            LOG.warn(ex.getMessage(), ex);
            return "не сегодня";
        }
    }
}
