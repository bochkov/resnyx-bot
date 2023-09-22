package com.sb.resnyxbot.services.rutor;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sb.resnyxbot.bot.ChooseScope;
import com.sb.resnyxbot.bot.ResnyxService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import resnyx.TgMethod;
import resnyx.messenger.general.Message;
import resnyx.messenger.general.SendMessage;

@Slf4j
@Service
@ChooseScope("rutor")
public final class Rutor implements ResnyxService {

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
    public List<TgMethod> answer(Message msg) {
        String chatId = String.valueOf(msg.getChat().getId());
        try {
            Torrent torrent = fetch(msg.getText());
            return torrent.toTgMethods(chatId);
        } catch (IOException ex) {
            return List.of(new SendMessage(chatId, ex.getMessage()));
        }
    }
}
