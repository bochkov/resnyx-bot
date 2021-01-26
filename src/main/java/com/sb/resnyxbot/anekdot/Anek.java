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

    private static final String ANEK_URL = "https://baneks.ru/random";

    private final String id;
    private final String text;

    public String text() {
        return text.replace("<br>", "\n");
    }

    @SneakyThrows
    public static Anek random() {
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
