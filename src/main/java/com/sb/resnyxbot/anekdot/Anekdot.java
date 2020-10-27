package com.sb.resnyxbot.anekdot;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import com.jcabi.xml.XMLDocument;
import com.sb.resnyxbot.ResnyxService;
import com.sb.resnyxbot.prop.Prop;
import com.sb.resnyxbot.prop.PropRepo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import resnyx.TgMethod;
import resnyx.methods.message.SendMessage;
import resnyx.model.Message;

@Slf4j
@Service
@RequiredArgsConstructor
public final class Anekdot implements ResnyxService {

    private static final DateTimeFormatter DTF = DateTimeFormatter.ofPattern("EEE, dd LLL yyyy HH:mm:ss Z", Locale.US);
    private static final String URL = "https://www.anekdot.ru/rss/export20.xml";

    private final PropRepo propRepo;

    @Override
    public List<TgMethod<Message>> answer(String token, Message msg) {
        return Collections.singletonList(
                new SendMessage(token, msg.getChat().getId(), daily().getText())
        );
    }

    @Scheduled(cron = "0 0 6-14 * * *")
    public void send() {
        Optional<Prop> tgToken = propRepo.findById("tg.token");
        Optional<Prop> chatId = propRepo.findById("chat.auto.send");
        if (tgToken.isEmpty()) {
            LOG.info("tg.token is empty. exit");
            return;
        }
        if (chatId.isEmpty()) {
            LOG.info("chat.auto.send is empty. exit");
            return;
        }
        Anek daily = daily();
        Optional<Prop> lastAnek = propRepo.findById("anekdot.lastDate");
        if (lastAnek.isPresent() && LocalDateTime.parse(lastAnek.get().getValue()).isAfter(daily.getDate())) {
            LOG.info("lastDate = {}, daily={}. Exit", lastAnek.get(), daily.getDate());
            return;
        }
        for (String chId : chatId.get().getValue().split(";")) {
            try {
                String text = String.format("Анекдот дня:%n%s", daily.getText());
                new SendMessage(
                        tgToken.get().getValue(),
                        Long.valueOf(chId),
                        text
                ).execute();
            } catch (IOException ex) {
                LOG.warn(ex.getMessage(), ex);
            }
        }
        propRepo.save(new Prop("anekdot.lastDate", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));
    }

    @SneakyThrows
    public Anek daily() {
        XMLDocument doc = new XMLDocument(new URI(URL));
        String pubDateTxt = doc.xpath("//channel/pubDate/text()").get(0);
        ZonedDateTime pubDate = ZonedDateTime.parse(pubDateTxt, DTF);
        LOG.debug("pubDate={}", pubDate);
        String anek = doc.xpath("//channel/item[1]/description/text()").get(0).replace("<br>", "\n");
        LOG.debug("anek ={}", anek);
        return new Anek(pubDate.toLocalDateTime(), anek);
    }
}
