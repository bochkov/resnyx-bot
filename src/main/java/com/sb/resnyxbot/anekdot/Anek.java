package com.sb.resnyxbot.anekdot;

import java.io.Serializable;
import java.net.URL;

import lombok.Data;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Data
public final class Anek implements Serializable {

    private static final String ANEK_RANDOM_URL = "https://baneks.ru/random";
    private static final String ANEK_URL = "https://baneks.ru/%d";

    private final String id;
    private final String text;

    public String text() {
        return text.replaceAll("<br>\\s*", "\n");
    }

    @SneakyThrows
    private static Anek get(String url) {
        Document doc = Jsoup.parse(new URL(url), 5000);
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

    @SneakyThrows
    public static Anek random() {
        return get(ANEK_RANDOM_URL);
    }

    @SneakyThrows
    public static Anek byId(Integer id) {
        return get(String.format(ANEK_URL, id));
    }
}
