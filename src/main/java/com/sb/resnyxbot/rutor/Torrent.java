package com.sb.resnyxbot.rutor;

import com.jcabi.http.Response;
import com.jcabi.http.request.JdkRequest;
import resnyx.TgMethod;
import resnyx.methods.message.SendDocument;
import resnyx.methods.message.SendMessage;
import resnyx.model.InputFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Torrent {

    public static final Torrent EMPTY = new Torrent("", "");

    private static final Pattern FN = Pattern.compile(".* filename=\"(.*)\"");

    private final String magnetUrl;
    private final String downloadUrl;

    public Torrent(String magnetUrl, String downloadUrl) {
        this.magnetUrl = magnetUrl;
        this.downloadUrl = downloadUrl;
    }

    public List<TgMethod> toTgMethods(String token, Long chatId) throws IOException {
        if (this.equals(EMPTY)) {
            return Collections.singletonList(
                    new SendMessage(token, chatId,
                            "Не получилось определить документ для скачивания")
            );
        } else {
            Response resp = new JdkRequest(downloadUrl).fetch();
            byte[] bytes = resp.binary();
            String header = resp.headers()
                    .computeIfAbsent("Content-Disposition", s -> Collections.singletonList(""))
                    .get(0);
            Matcher m = FN.matcher(header);
            String name = m.find() ? m.group(1) : "noname";
            return Arrays.asList(
                    new SendMessage(token, chatId, magnetUrl),
                    new SendDocument(token, chatId, new InputFile(bytes, name))
            );
        }
    }
}
