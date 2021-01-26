package com.sb.resnyxbot.anekdot;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.sb.resnyxbot.ResnyxService;
import com.sb.resnyxbot.prop.Prop;
import com.sb.resnyxbot.prop.PropRepo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import resnyx.TgMethod;
import resnyx.methods.message.SendMessage;
import resnyx.model.Message;

@Slf4j
@Service
@RequiredArgsConstructor
public final class Anekdot implements ResnyxService {

    private static final String ANEK_URL = "https://baneks.ru/random";

    private final PropRepo propRepo;

    @Override
    public List<TgMethod<Message>> answer(String token, Message msg) {
        SendMessage reply = new SendMessage(token, msg.getChat().getId(), random().getText());
        reply.setParseMode("html");
        return Collections.singletonList(reply);
    }

    @Scheduled(cron = "0 0 9 * * *")
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
        Anek anek = random();
        for (String chId : chatId.get().getValue().split(";")) {
            try {
                String text = String.format("Анекдот дня:%n%s", anek.getText());
                SendMessage msg = new SendMessage(
                        tgToken.get().getValue(),
                        Long.valueOf(chId),
                        text
                );
                msg.setParseMode("html");
                msg.execute();
            } catch (IOException ex) {
                LOG.warn(ex.getMessage(), ex);
            }
        }
    }

    @SneakyThrows
    public Anek random() {
        Document doc = Jsoup.parse(new URL(ANEK_URL), 5000);
        String id = doc.getElementsByTag("main").get(0).attr("data-id");
        String text = "";
        for (Element view : doc.getElementsByClass("anek-view")) {
            Elements articles = view.getElementsByTag("article");
            if (!articles.isEmpty()) {
                text = articles.get(0).getElementsByTag("p").get(0).html();
            }
        }
        return new Anek(id, text);
    }
}
