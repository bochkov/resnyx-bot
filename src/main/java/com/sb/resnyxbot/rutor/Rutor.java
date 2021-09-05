package com.sb.resnyxbot.rutor;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sb.resnyxbot.ChooseScope;
import com.sb.resnyxbot.ResnyxService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import resnyx.TgMethod;
import resnyx.methods.message.SendMessage;
import resnyx.model.Message;

@Slf4j
@Service
@ChooseScope("rutor")
public class Rutor implements ResnyxService {

    private static final Pattern URL = Pattern.compile("https?://.*");

    public Torrent fetch(String rawUrl) throws IOException {
        Matcher m = URL.matcher(rawUrl);
        if (m.find()) {
            Document doc = Jsoup.connect(m.group()).get();
            Element downloadSection = doc.getElementById("download");
            if (downloadSection == null) {
                LOG.warn("download section is empty");
                return Torrent.EMPTY;
            }
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
