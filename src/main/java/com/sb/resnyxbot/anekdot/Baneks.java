package com.sb.resnyxbot.anekdot;

import java.net.URL;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public final class Baneks implements AnekdotService {

    private static final String ANEK_RANDOM_URL = "https://baneks.ru/random";
    private static final String ANEK_URL = "https://baneks.ru/%d";

    @SneakyThrows
    private Anek get(String url) {
        Document doc = Jsoup.parse(new URL(url), 5000);
        String id = doc.getElementsByTag("main").get(0).attr("data-id");
        String text = "";
        for (Element view : doc.getElementsByClass("anek-view")) {
            Elements articles = view.getElementsByTag("article");
            if (!articles.isEmpty()) {
                text = articles.get(0)
                        .getElementsByTag("p").get(0)
                        .html().replaceAll("<br>\\s*", "\n");
            }
        }
        return new Anek(id, text);
    }

    @SneakyThrows
    @Override
    public Anek random() {
        return get(ANEK_RANDOM_URL);
    }

    @SneakyThrows
    @Override
    public Anek byId(Integer id) {
        return get(String.format(ANEK_URL, id));
    }

}
