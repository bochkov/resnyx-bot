package com.sb.resnyxbot.forismatic;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.sb.resnyxbot.ChooseScope;
import com.sb.resnyxbot.ResnyxService;
import com.sb.resnyxbot.prop.PropRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import resnyx.TgMethod;
import resnyx.methods.message.SendMessage;
import resnyx.model.Message;

@Slf4j
@Service
@RequiredArgsConstructor
@ChooseScope("цитат")
public class Forismatic implements ResnyxService {

    private final PropRepo propRepo;

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
                                                    String text = String.format("Мудрость дня:%n%s", cite());
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

    public String cite() {
        RestTemplate rest = new RestTemplate();
        UriComponents uris = UriComponentsBuilder.fromHttpUrl("https://api.forismatic.com/api/1.0/")
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
            LOG.warn(ex.getMessage(), ex);
            return "не сегодня";
        }
    }

    @Override
    public List<TgMethod<Message>> answer(String token, Message msg) {
        return Collections.singletonList(
                new SendMessage(token, msg.getChat().getId(), cite())
        );
    }
}
