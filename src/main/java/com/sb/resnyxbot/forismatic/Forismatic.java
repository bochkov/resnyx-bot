package com.sb.resnyxbot.forismatic;

import com.sb.resnyxbot.prop.PropRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import resnyx.methods.message.SendMessage;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Service
public class Forismatic {

    private final PropRepo propRepo;

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
                                                    log.warn(ex.getMessage(), ex);
                                                }
                                            }
                                        },
                                        () -> log.info("chat.auto.send is empty. exit")
                                ),
                        () -> log.info("tg.token is empty. exit")
                );
    }

    public String get() {
        RestTemplate rest = new RestTemplate();
        UriComponents uris = UriComponentsBuilder.fromHttpUrl("http://api.forismatic.com/api/1.0/")
                .queryParam("method", "getQuote")
                .queryParam("format", "text")
                .queryParam("lang", "ru")
                .build();
        try {
            ResponseEntity<String> response = rest.getForEntity(uris.toUriString(), String.class);
            if (response.getStatusCode() != HttpStatus.OK)
                throw new RestClientException("status = " + response.getStatusCode());
            return response.getBody();
        } catch (RestClientException ex) {
            log.warn(ex.getMessage(), ex);
            return "не сегодня";
        }
    }
}
