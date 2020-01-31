package com.sb.resnyxbot.rutor;

import com.sb.resnyxbot.ResnyxService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import resnyx.TgMethod;
import resnyx.methods.message.SendMessage;
import resnyx.model.Message;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class Rutor implements ResnyxService {

    private static final Pattern URL = Pattern.compile("https?://.*");

    public Torrent fetch(String rawUrl) throws IOException {
        Matcher m = URL.matcher(rawUrl);
        if (m.find()) {
            Document doc = Jsoup.connect(m.group()).get();
            Element downloadSection = doc.getElementById("download");
            String magnetUrl = "";
            String downloadUrl = "";
            for (Element aElem : downloadSection.getElementsByTag("a")) {
                String href = aElem.attr("href");
                if (href.startsWith("magnet:"))
                    magnetUrl = href;
                if (href.startsWith("http"))
                    downloadUrl = href;
            }
            return new Torrent(magnetUrl, downloadUrl);
        } else
            return Torrent.EMPTY;
    }

    @Override
    public List<TgMethod<Message>> answer(String token, Message msg) {
        try {
            return fetch(msg.getText())
                    .toTgMethods(token, msg.getChat().getId());
        } catch (IOException ex) {
            return Collections.singletonList(
                    new SendMessage(token, msg.getChat().getId(), ex.getMessage())
            );
        }
    }
}
